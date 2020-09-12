package bgu.spl.mics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class Printer {

    private static ConcurrentHashMap<String , Object> locksMap = new ConcurrentHashMap<>();

    public static void print(String filename, Object toPrint) {
        if (filename != null && !filename.equals("")) {
            locksMap.putIfAbsent(filename, new Object());

            synchronized (locksMap.get(filename)) {

                Gson g = new GsonBuilder().setPrettyPrinting().create();

                try (FileWriter fileWriter = new FileWriter(filename)) {
                    g.toJson(toPrint, fileWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
