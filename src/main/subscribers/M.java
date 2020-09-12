package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
    private Diary diary;
    private AtomicInteger currentTick;
    private MessageBroker mb;
    private Integer id;
    private int totalDuration;

    public M(Integer id) {
        super("M");
        this.id = id;
        currentTick = new AtomicInteger(0);
        mb = MessageBrokerImpl.getInstance();
        diary = Diary.getInstance();
        totalDuration = -1;
    }


    @Override
    protected void initialize()  {
        subscribeBroadcast(TickBroadcast.class, (tickBroadCast) -> {
            currentTick = tickBroadCast.getCurrentTick();
            totalDuration = tickBroadCast.getTotalDuration();
        });
        subscribeBroadcast(TerminateBroadcast.class,(b)->{
            terminate();
        });
        subscribeEvent(MissionReceivedEvent.class, (msg) -> {
            diary.incrementTotal();
            int tick = msg.getTick().intValue();
            Integer idMp=null;
            AtomicInteger qT=null;
            List<String> agentsNames;

            if (currentTick.intValue() < totalDuration) {
                AgentAvailableEvent agentAvailableEvent = new AgentAvailableEvent(msg.getMissionInfo().getSerialAgentsNumbers(), msg.missionInfo.getTimeExpired(), msg.missionInfo.getDuration());
                Future<Integer> idMoney = mb.sendEvent(agentAvailableEvent);
                if(idMoney == null){
                    terminate();
                }
                else{
                    idMp = idMoney.get(totalDuration - currentTick.intValue(), TimeUnit.MILLISECONDS);
                    if(idMp==null)
                        terminate();
                }

                if (currentTick.intValue() < totalDuration && idMoney != null && idMp != null) {
                    GadgetAvailableEvent gadgetAvailableEvent = new GadgetAvailableEvent(msg.getMissionInfo().getGadget());
                    Future<AtomicInteger> qTime = mb.sendEvent(gadgetAvailableEvent);
                    if(qTime==null){
                        terminate();
                    }
                    else{
                        qT = qTime.get();
                        if(qT==null)
                            terminate();
                    }
                    if (qTime != null && qT != null && currentTick.intValue() < totalDuration &&  currentTick.intValue() < msg.getMissionInfo().getTimeExpired()) {
                        SendAgentsEvent sendAgentsEvent = new SendAgentsEvent(msg.getMissionInfo().getSerialAgentsNumbers(), msg.getMissionInfo().getDuration());
                        Future<List<String>> agents = mb.sendEvent(sendAgentsEvent);
                        if (agents == null) {
                            terminate();
                        }
                        else {
                            agentsNames = agents.get();
                            if (agentsNames != null) {
                                Report r = new Report(msg.getMissionInfo().getMissionName(), id.toString(), idMp.toString(), msg.getMissionInfo().getSerialAgentsNumbers(), agentsNames, msg.getMissionInfo().getGadget(), tick, qT.intValue(), currentTick.intValue());
                                diary.addReport(r);
                                complete(msg, true);
                                mb.sendEvent(new ReleaseAgentEvent(msg.missionInfo.getSerialAgentsNumbers()));
                            } else {
                                complete(msg, false);
                                mb.sendEvent(new ReleaseAgentEvent(msg.missionInfo.getSerialAgentsNumbers()));
                            }
                        }
                    }
                    else {
                        mb.sendEvent(new ReleaseAgentEvent(msg.missionInfo.getSerialAgentsNumbers()));
                        complete(msg, false);
                    }
                }
                else{
                    mb.sendEvent(new ReleaseAgentEvent(msg.missionInfo.getSerialAgentsNumbers()));
                    complete(msg, false);
                }
            }
            else{
                complete(msg, false);
            }
        });
    }
}

