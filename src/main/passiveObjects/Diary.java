package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Printer;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	private List<Report> reports = new LinkedList<>();
	private AtomicInteger total = new AtomicInteger(0);

	private static class SingletonHolder {
		private static Diary instance = new Diary();
	}

	private Diary(){
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Diary getInstance() {
		return SingletonHolder.instance;
	}

	public List<Report> getReports() {
		return reports;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public void addReport(Report reportToAdd){
		synchronized (reports) {
			reports.add(reportToAdd);
		}
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename) throws IOException {
		Printer.print(filename,this);
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){
		return total.intValue();
	}

	/**
	 * Increments the total number of received missions by 1
	 */
	public void incrementTotal()
	{
		total.incrementAndGet();
	}


}
