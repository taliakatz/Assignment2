package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import java.util.concurrent.atomic.AtomicInteger;

public class GadgetAvailableEvent implements Event<AtomicInteger> {
    public String gadget;

    public GadgetAvailableEvent(String gadget){
        this.gadget=gadget;
    }
}
