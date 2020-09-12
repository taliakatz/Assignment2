package bgu.spl.mics.application.subscribers;
import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private LinkedList<MissionInfo> infoList;
	private Integer id;
	private AtomicInteger currentTick;
	private MessageBroker mb;
	private int duration;



	public Intelligence(Integer id, LinkedList<MissionInfo> missionsList, int duration) {
		super("Intelligence");
		this.id = id;
		mb = MessageBrokerImpl.getInstance();
		infoList = missionsList;
		infoList.sort(Comparator.comparingInt(MissionInfo::getTimeIssued));
		currentTick = new AtomicInteger(0);
		this.duration = duration;
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (tickBroadCast)->{
			currentTick=tickBroadCast.getCurrentTick();
			int index=0;
			while(currentTick.intValue() < duration && index < infoList.size()) {
				while (currentTick.intValue() == infoList.get(index).getTimeIssued()) {
					mb.sendEvent(new MissionReceivedEvent(infoList.get(index), currentTick));
					index = index + 1;
				}
			}
		});
		subscribeBroadcast(TerminateBroadcast.class,(b)->{
			terminate();
		});
	}
}