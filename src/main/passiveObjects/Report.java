package bgu.spl.mics.application.passiveObjects;

import java.util.List;

/**
 * Passive data-object representing a delivery vehicle of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Report {

    private String missionName;
    private String m;
    private String moneypenny;
    private List<String> agentsSerialNumbers;
    private List<String> agentsNames;
    private String gadgetName;
    private int timeCreated;
    private int timeIssued;
    private int qTime;

    public Report(String missionName, String idM, String idMoneypenny, List<String> agentsSerialNumbers, List<String> agentsNames, String gadget, int timeIssued, int QTime, int timeCreated) {
        this.missionName = missionName;
        this.m = idM;
        this.moneypenny = idMoneypenny;
        this.agentsSerialNumbers = agentsSerialNumbers;
        this.agentsNames = agentsNames;
        this.gadgetName = gadget;
        this.timeIssued = timeIssued;
        this.qTime = QTime;
        this.timeCreated = timeCreated;
    }

    /**
     * Retrieves the mission name.
     */
    public String getMissionName() {
        return missionName;
    }

    /**
     * Sets the mission name.
     */
    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    /**
     * Retrieves the M's id.
     */
    public String getM() {
        return m;
    }

    /**
     * Sets the M's id.
     */
    public void setM(String m) {
        this.m = m;
    }

    /**
     * Retrieves the Moneypenny's id.
     */
    public String getMoneypenny() {
        return moneypenny;
    }

    /**
     * Sets the Moneypenny's id.
     */
    public void setMoneypenny(String moneypenny) {
        this.moneypenny = moneypenny;
    }

    /**
     * Retrieves the serial numbers of the agents.
     * <p>
     *
     * @return The serial numbers of the agents.
     */
    public List<String> getAgentsSerialNumbersNumber() {
        return agentsSerialNumbers;
    }

    /**
     * Sets the serial numbers of the agents.
     */
    public void setAgentsSerialNumbersNumber(List<String> agentsSerialNumbersNumber) {
        agentsSerialNumbers = agentsSerialNumbersNumber;
    }

    /**
     * Retrieves the agents names.
     * <p>
     *
     * @return The agents names.
     */
    public List<String> getAgentsNames() {
        return agentsNames;
    }

    /**
     * Sets the agents names.
     */
    public void setAgentsNames(List<String> agentsNames) {
        this.agentsNames = agentsNames;
    }

    /**
     * Retrieves the name of the gadget.
     * <p>
     *
     * @return the name of the gadget.
     */
    public String getGadgetName() {
        return gadgetName;
    }

    /**
     * Sets the name of the gadget.
     */
    public void setGadgetName(String gadgetName) {
        this.gadgetName = gadgetName;
    }

    /**
     * Retrieves the time-tick in which Q Received the GadgetAvailableEvent for that mission.
     */
    public int getqTime() {
        return qTime;
    }

    /**
     * Sets the time-tick in which Q Received the GadgetAvailableEvent for that mission.
     */
    public void setqTime(int qTime) {
        this.qTime = qTime;
    }

    /**
     * Retrieves the time when the mission was sent by an Intelligence Publisher.
     */
    public int getTimeIssued() {
        return timeIssued;
    }

    /**
     * Sets the time when the mission was sent by an Intelligence Publisher.
     */
    public void setTimeIssued(int timeIssued) {
        this.timeIssued = timeIssued;
    }

    /**
     * Retrieves the time-tick when the report has been created.
     */
    public int getTimeCreated() {
        return timeCreated;
    }

    /**
     * Sets the time-tick when the report has been created.
     */
    public void setTimeCreated(int timeCreated) {
        this.timeCreated = timeCreated;
    }
}
