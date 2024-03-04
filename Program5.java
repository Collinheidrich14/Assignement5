package accidentpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * This Java file reads the cvs file and output the information into a BTS. The tree can then be searched and information is gathered from it
*  Some sources I used where https://www.youtube.com/watch?v=GzJoqJO1zdI, https://www.youtube.com/watch?v=M6lYob8STMI, and using chatgpt for basic things to save time.
* @author Collin Heidrich, Jesse Cyrbrophy
* @version Febuary 28 2021
*/

public class Program5 {
	private static final String READABLE_DATETIME_FORMAT = "MM-dd-yyyy H:mm:ss";

	private static final Map<String, MyBST> collectionOfTrees = new HashMap<>();

	public static void main(String[] args) {
		executePopulatingBST();
		calculateReportsAfterDate();
//		String promptCSV = prompt();
	}
/**
 * A method to populate the BTS with accidents.csv
 * Reads the data from CSV file to a BTS and 
 * calculates the size of the BTS
 */
	private static void executePopulatingBST() {
		// converting incoming string startDate into a LocalDateTime object
//		final LocalDateTime startDateTime = dateFormatter((startDate + " 00:00:01").trim(), READABLE_DATETIME_FORMAT);

		Instant start = null, end = null;
		try (BufferedReader br = new BufferedReader(new FileReader("accidents.csv"))) {
			br.readLine(); 
			String line;
			start = Instant.now();
			while ((line = br.readLine()) != null) {
				String[] rawData = line.split(",");
				Report report = new Report(rawData[0], rawData[1],
						dateFormatter(rawData[2].trim(), READABLE_DATETIME_FORMAT),
						dateFormatter(rawData[3].trim(), READABLE_DATETIME_FORMAT), rawData[4], rawData[5], rawData[6],
						rawData[7], rawData[8], rawData[9], Double.parseDouble(rawData[10]), rawData[11], rawData[12],
						rawData[13]);
				if (!collectionOfTrees.containsKey(report.getState())) {
					System.out.printf("New BST State added: %s\n", report.getState());
					MyBST newBST = new MyBST();
					newBST.insert(report);
					collectionOfTrees.put(report.getState(), newBST);
				} else {
					MyBST bst = collectionOfTrees.get(report.getState());
					bst.insert(report);
					collectionOfTrees.put(report.getState(), bst);
				}
			}
			end = Instant.now();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			System.out.printf("Total Time to build the BST: %s\n", Duration.between(start, end).getNano());
			BigInteger z = BigInteger.ZERO;
			
			for(MyBST v : collectionOfTrees.values()){
//				System.out.printf("State: %s \n", v.size());
				z = z.add(new BigInteger(v.size()+""));
			};
			System.out.println("Total size: "+z.toString());
			
		}
	}
	/**
	 * A method to calculate the number of inputs after
	 * user input state and date
	 */
	 private static void calculateReportsAfterDate() {
	        try (Scanner scanner = new Scanner(System.in)) {
	            System.out.print("Enter the state (e.g., IL): ");
	            String state = scanner.nextLine();
	            System.out.print("Enter the date (e.g., 2022-09-08): ");
	            String dateStr = scanner.nextLine();

	            LocalDateTime dateTime = LocalDateTime.parse(dateStr + "T00:00:00");
	            Report dateReport = new Report("", "", dateTime, dateTime, "", "", "", state, "", "", 0.0, "", "", "");

	            MyBST bst = collectionOfTrees.get(state);
	            if (bst != null) {
	                Instant start = Instant.now();
	                int count = bst.countReportsAfterDate(dateReport);
	                Instant end = Instant.now();
	        		double timeInNanoSeconds = Duration.between(start, end).getNano();
	        		System.out.println(count+" reports are available for "+state+" on and after the date: " + dateStr);
	                System.out.println((double)( timeInNanoSeconds)/1000000000 +  " seconds to calculate this using children count fields");   
	                if (bst != null) {
	                    Instant start1 = Instant.now();
	                     count = bst.countReportsAfterDateRecursive(bst.root,dateReport.getStartTime());
	                    Instant end1 = Instant.now();
		        		double timeInNanoSeconds1 = Duration.between(start1, end1).getNano();
		                System.out.println(count+" reports are available for "+state+" on and after the date: " + dateStr);
		                System.out.println( (double)(timeInNanoSeconds1)/1000000000 +  " seconds to calculate this using recursive method");   
	         
	                }} else {
	                System.out.println("No data found for the specified state.");
	                }
	       
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	

	private static String prompt() {
		StringBuilder sb = new StringBuilder();
		try (Scanner z = new Scanner(System.in)) {
			System.out.print("Enter state (IL or CA): ");
			String state = z.nextLine();
			sb.append(state + ",");
			System.out.print("Enter date (YYYY-MM-DD): ");
			String date = z.nextLine();
			sb.append(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();

	}

	/**
	 * Method to parse start and end time in accidents (3).csv
	 * 
	 * @param dateTimeStr start and end time
	 * @param format      the pattern and symbols represented in CSV file.
	 * @return formatted date
	 */
	private static LocalDateTime dateFormatter(String dateTimeStr, String format) {
		if (dateTimeStr.contains(" ") && format != null) {
			String date = handleDateFormat(dateTimeStr.split(" ")[0]);
			String time = handleTimestamp(dateTimeStr.split(" ")[1]);
			final String dateTIme = String.format("%s %s", date, time);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
			return LocalDateTime.parse(dateTIme, dtf);
		}
		return LocalDateTime.now();
	}

	/**
	 * Method to handle the string of data represented in accidents.csv (3) split by
	 * year, month, day
	 * 
	 * @param dateStr represents the dates in Report
	 * @return formatted date
	 */
	private static String handleDateFormat(String dateStr) {
	    if (!dateStr.isEmpty()) {

		String[] dateSplit = dateStr.contains("/") ? dateStr.split("/") : dateStr.split("-");
		String year = String.format("%4d", Integer.parseInt(dateSplit[0]));
		String month = String.format("%02d", Integer.parseInt(dateSplit[1]));
		String day = String.format("%02d", Integer.parseInt(dateSplit[2]));
		return String.format("%s-%s-%s", month, day, year);
	} else {
        return ""; // Return empty string if input is empty
    }
}
	/**
	 * Regular Expression that matches characters \. and replaces with empty string
	 * 
	 * @param timeStr string of data in accidents (3).csv
	 * @return replaced type
	 */
	private static String handleTimestamp(String timeStr) {
		String time = timeStr.replaceAll("\\..*", "");
		return String.format("%s", time);
	}

}
