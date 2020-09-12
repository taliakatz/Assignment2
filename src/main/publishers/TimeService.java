package bgu.spl.mics.application.publishers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
    private MessageBroker mb;
    private AtomicInteger tick;
    private int totalDuration;

    public TimeService(int totalDuration) {
        super("Timer");
        tick = new AtomicInteger(0);
        this.totalDuration = totalDuration;
        mb = MessageBrokerImpl.getInstance();
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void run() {
        try {
            while (tick.intValue() < totalDuration) {
                tick.incrementAndGet();
                mb.sendBroadcast(new TickBroadcast(tick, totalDuration));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        mb.sendBroadcast(new TerminateBroadcast());
    }
}
