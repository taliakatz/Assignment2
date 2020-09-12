package bgu.spl.mics.application.passiveObjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

    private HashMap<String, Agent> agents;

    private static class SingletonHolder {
        private static Squad instance = new Squad();
    }

    private Squad() {
        agents = new HashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Squad getInstance() {
        return Squad.SingletonHolder.instance;
    }

    /**
     * Initializes the squad. This method adds all the agents to the squad.
     * <p>
     *
     * @param agents Data structure containing all data necessary for initialization
     *               of the squad.
     */
    public void load(Agent[] agents) {
        for (int i = 0; i < agents.length; i++)
            this.agents.put(agents[i].getSerialNumber(), agents[i]);
    }

    /**
     * Releases agents.
     */
    public void releaseAgents(List<String> serials) {
        Collection<String> sers = agents.keySet();
        if (serials != null) {
            sers = serials;
        }

        for (String s : sers) {
            if (agents.containsKey(s)) {
                synchronized (agents.get(s)) {
                    agents.get(s).release();
                    agents.get(s).notifyAll();
                }
            }
        }

    }

    /**
     * simulates executing a mission by calling sleep.
     *
     * @param time milliseconds to sleep
     */


    public void sendAgents(List<String> serials, int time) throws InterruptedException {
        try {
            Thread.currentThread().sleep(time * 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        releaseAgents(serials);
    }

    /**
     * acquires an agent, i.e. holds the agent until the caller is done with it
     *
     * @param serials the serial numbers of the agents
     * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
     */


    public boolean getAgents(List<String> serials) {
        for (String s : serials) {
            if (agents.get(s) == null)
                return false;
        }
        serials.sort(String::compareTo); // we want to sort the serial number to avoid collusion between to threads wanting the same agents
        try {

            for (String s : serials) {
                synchronized (agents.get(s)) {
                    while (!agents.get(s).isAvailable()) {
                        agents.get(s).wait();
                    }
                    agents.get(s).acquire();
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return true;
    }


    /**
     * gets the agents names
     *
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials) {
        List<String> nameList = new LinkedList<>();
        for (String s : serials)
            nameList.add(agents.get(s).getName());
        return nameList;
    }
}
