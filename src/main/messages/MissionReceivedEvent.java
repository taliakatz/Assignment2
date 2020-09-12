package bgu.spl.mics.application.messages;


import bgu.spl.mics.AtomicCounter;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MissionReceivedEvent implements Event<Boolean> {
    public MissionInfo missionInfo;
    AtomicInteger tick;

    public MissionReceivedEvent (MissionInfo missionInfo, AtomicInteger tick) {
        this.missionInfo = missionInfo;
        this.tick = tick;
    }

    public MissionInfo getMissionInfo() {
        return missionInfo;
    }
    public AtomicInteger getTick(){
        return tick;
    }
}
