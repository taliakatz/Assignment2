package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SquadTest {

    Squad s = Squad.getInstance();
    Agent[] arr=new Agent[9];

    @BeforeEach
    public void setUp(){
        for (int i = 0; i < 9; i++) {
            arr[i] = new Agent("", "");
            arr[i].setName("00"+i);
            arr[i].setSerialNumber("00"+i);
        }
    }

    @Test
    public void test(){
        getInstance();
        getAgents();
        getAgentsNames();
    }


    @Test
    void getInstance() {
        assertNotNull(s);
        Object tmp = Squad.getInstance();
        assertNotNull(tmp);
        assertEquals(s, tmp);
        assertSame(s, tmp);
    }


    @Test
    void getAgents() {
        s.load(arr);
        List<String> sers=new LinkedList<>();
        for (int i = 1; i < arr.length-1; i++) {
            sers.add(arr[i].getSerialNumber());
        }
        assertTrue(s.getAgents(sers));

        sers.clear();

        for (int i = 0; i < arr.length; i++) {
            sers.add(arr[i].getSerialNumber()+"a");
        }
        assertFalse(s.getAgents(sers));
    }

    @Test
    void getAgentsNames() {
        List<String> sers=new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            sers.add(arr[i].getSerialNumber());
        }
        List<String> gotNames = s.getAgentsNames(sers);
        for(int i=0; i<arr.length; i++){
            assertEquals(arr[i].getName(), gotNames.get(i));
        }
        arr[1].setName("talia&adi");
        assertNotEquals(001, arr[1].getName());
    }

}
