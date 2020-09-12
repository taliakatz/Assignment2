package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

import java.util.concurrent.atomic.AtomicInteger;

public class TickBroadcast implements Broadcast {

    private AtomicInteger currentTick;
    private int totalDuration;

    public TickBroadcast(AtomicInteger tick, int totalDuration){
        currentTick = tick;
        this.totalDuration = totalDuration;
    }

    public AtomicInteger getCurrentTick() {
        return currentTick;
    }

    public int getTotalDuration(){
        return totalDuration;
    }
}
