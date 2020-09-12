package bgu.spl.mics.application.subscribers;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private MessageBroker mb;
	private Inventory inventory;
	AtomicInteger currentTick ;

	public Q() {
		super("Q");
		mb = MessageBrokerImpl.getInstance();
		inventory = Inventory.getInstance();
		currentTick=new AtomicInteger(0);
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (tickBroadCast)->{
			currentTick=tickBroadCast.getCurrentTick();
		});
		subscribeBroadcast(TerminateBroadcast.class,(b)->{
			terminate();
		});
		subscribeEvent(GadgetAvailableEvent.class,(msg)->{
			if(inventory.getItem(msg.gadget)){
				complete(msg, currentTick);
			}
			else{
				complete(msg, null);
			}
		});
	}
}
