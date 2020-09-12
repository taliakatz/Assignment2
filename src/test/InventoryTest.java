package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inv;

    @BeforeEach
    public void setUp() {
        inv = Inventory.getInstance();
    }

    @Test
    public void test() throws IOException {
        getInstance();
        load();
        getItem();
        printToFile();
    }

    @Test
    void getInstance() {
        assertNotNull(inv);
        Object tmp = Inventory.getInstance();
        assertNotNull(tmp);
        assertEquals(inv, tmp);
        assertSame(inv, tmp);
    }

    @Test
    void load() {
        String[] arr = {"A", "", null, "aaa"};
        inv.load(arr);
        assertTrue(inv.getItem("A"));
        assertTrue(inv.getItem(null));
        assertFalse(inv.getItem("gun"));
        assertFalse(inv.getItem("null"));
    }

    @Test
    void getItem() {
        assertDoesNotThrow( () -> { inv.getItem("B");  });
        assertDoesNotThrow( () -> {inv.getItem("A"); } );
        assertDoesNotThrow(() -> {inv.getItem("a");});
        assertDoesNotThrow(() -> { inv.getItem(null);});
    }

    @Test
    void printToFile() throws IOException {
        assertDoesNotThrow(() -> { inv.printToFile(null);});
        assertDoesNotThrow(() -> { inv.printToFile("");});
        assertDoesNotThrow(() -> { inv.printToFile("aaa");});
        inv.printToFile("aaa.json");
        assertTrue(new File("aaa.json").exists());
    }
}
