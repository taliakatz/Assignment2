package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {

	private AtomicInteger currentTick;
	private MessageBroker mb;
	private Squad squad;
	private Integer id;

	public Moneypenny(Integer id) {
		super("Moneypenny");
		this.id = id;
		mb = MessageBrokerImpl.getInstance();
		squad = Squad.getInstance();
		currentTick = new AtomicInteger(0);
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (tickBroadCast) -> {
			currentTick = tickBroadCast.getCurrentTick();
		});
		subscribeBroadcast(TerminateBroadcast.class, (b) -> {
			squad.releaseAgents(null);
			terminate();
		});
		if (id % 2 == 0) {
			subscribeEvent(AgentAvailableEvent.class, (msg) -> {
				if (currentTick.intValue() < msg.getTimeExpired() ) {
					if(squad.getAgents(msg.getAgentsSerialNumbers())) {
						complete(msg, id);
						return;
					}
				}
				complete(msg, null);
			});
		}
		else {
			subscribeEvent(SendAgentsEvent.class, (msg) -> {
				complete(msg, squad.getAgentsNames(msg.getSerialNumbers()));
				squad.sendAgents(msg.getSerialNumbers(), msg.getDuration());
			});
			subscribeEvent(ReleaseAgentEvent.class, (msg) -> {
				complete(msg, true);
				squad.releaseAgents(msg.getSerialNumbers());
			});
		}
	}
}
