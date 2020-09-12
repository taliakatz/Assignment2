package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import java.util.List;

public class AgentAvailableEvent implements Event<Integer> {

    private List<String> agentsSerialNumbers;
    private int timeExpired;

    public AgentAvailableEvent (List<String> agentsSerialNumbers, int timeExpired, int duration){
        this.agentsSerialNumbers = agentsSerialNumbers;
        this.timeExpired = timeExpired;
    }
    public List<String> getAgentsSerialNumbers(){
        return agentsSerialNumbers;
    }

    public int getTimeExpired(){
        return timeExpired;
    }



}
