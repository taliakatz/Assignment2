package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class ReleaseAgentEvent implements Event<Boolean> {

    private List<String> serialNumbers;

    public ReleaseAgentEvent( List<String> AgentsSerialNumbers){
        this.serialNumbers = AgentsSerialNumbers;
    }
    public List<String> getSerialNumbers(){
        return serialNumbers;
    }
}
