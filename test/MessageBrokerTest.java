package bgu.spl.mics;

import bgu.spl.mics.application.subscribers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageBrokerTest {
    MessageBroker mb;
    M m=new M(1);
    Q q=new Q();
    Moneypenny mp=new Moneypenny(1);
    @BeforeEach
    public void setUp(){
        mb=MessageBrokerImpl.getInstance();
    }


    @Test
    public void test(){
        subscribeEvent();
        subscribeBroadcast();
        register();
        unregister();
    }

    void subscribeEvent() {
    }

    void subscribeBroadcast() {
    }


    void register() {
        assertDoesNotThrow(()->{mb.register(m);});
        assertDoesNotThrow(()->{mb.register(q);});
        assertDoesNotThrow(()->{mb.register(mp);});
        assertDoesNotThrow(()->{mb.register(new M(2));});
        assertDoesNotThrow(()->{mb.register(new Q());});
        assertDoesNotThrow(()->{mb.register(new M(3));});

    }

    void unregister() {
        assertDoesNotThrow(()->{mb.unregister(m);});
        assertDoesNotThrow(()->{mb.unregister(q);});
        assertDoesNotThrow(()->{mb.unregister(mp);});
    }
}
