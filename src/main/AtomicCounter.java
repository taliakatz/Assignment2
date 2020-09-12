package bgu.spl.mics;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private static AtomicInteger counter=new AtomicInteger(0);

    private static class SingletonHolder {
        private static AtomicCounter instance = new AtomicCounter();
    }
    private AtomicCounter(){ }

    public static AtomicCounter getInstance() {
        return AtomicCounter.SingletonHolder.instance;
    }
    public AtomicInteger get(){
        return counter;
    }

}
