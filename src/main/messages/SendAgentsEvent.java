package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import java.util.List;

public class SendAgentsEvent implements Event<List<String>> {
    private List<String> serialNumbers;
    private int duration;


    public SendAgentsEvent(List<String> serialNumbers, int duration){
        this.serialNumbers = serialNumbers;
        this.duration = duration;
    }

    public List<String> getSerialNumbers(){
        return serialNumbers;
    }

    public int getDuration(){
        return duration;
    }



}
