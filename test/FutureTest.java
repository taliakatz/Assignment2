package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future<Integer> f;
    Future<Integer> f2;
    @BeforeEach
    public void setUp(){
         f = new Future<>();
         f2 = new Future<>();
    }

    @Test
    public void test(){
        isDone();
        testGet();
    }

    void isDone() {
        assertFalse(f.isDone());
        f.resolve(4);
        assertTrue(f.isDone());
        assertFalse(f2.isDone());
    }
    void testGet() {
         f.resolve(12);
        assertEquals(12, f.get());
        assertEquals(12, f.get(0, TimeUnit.MILLISECONDS));
    }
}
