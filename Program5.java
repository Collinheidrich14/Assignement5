package accidentpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/**
 * This Java file reads the cvs file and output the information into a binary tree. The tree can then be searched and information is gathered from it
*  Some sources I used where https://www.youtube.com/watch?v=GzJoqJO1zdI, https://www.youtube.com/watch?v=M6lYob8STMI, and using chatgpt for basic things to save time.
* @author Collin Heidrich, Jesse Cyrbrophy
* @version Febuary 28 2021
*/
class AccidentReport {
    String id, severity, startTime, endTime, street, city, county, state, temperature, humidity, visibility, weatherCondition, crossing, sunriseSunset;

    /**
     * Constructor for creating an AccidentReport
     *
     * @param id               The unique identifier for the report.
     * @param severity         The severity of the accident.
     * @param startTime        The start time of the accident.
     * @param endTime          The end time of the accident.
     * @param street           The street where the accident occurred.
     * @param city             The city where the accident occurred.
     * @param county           The county where the accident occurred.
     * @param state            The state where the accident occurred.
     * @param temperature      The temperature at the time of the accident.
     * @param humidity         The humidity at the time of the accident.
     * @param visibility       The visibility at the time of the accident.
     * @param weatherCondition The weather condition at the time of the accident.
     * @param crossing         The crossing details at the accident location.
     * @param sunriseSunset    The information about sunrise and sunset. 
     */
    public AccidentReport(String id, String severity, String startTime, String endTime, String street, String city, String county, String state, String temperature, String humidity, String visibility, String weatherCondition, String crossing, String sunriseSunset) {
        this.id = id;
        this.severity = severity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.street = street;
        this.city = city;
        this.county = county;
        this.state = state;
        this.temperature = temperature;
        this.humidity = humidity;
        this.visibility = visibility;
        this.weatherCondition = weatherCondition;
        this.crossing = crossing;
        this.sunriseSunset = sunriseSunset;
    }
    /**
     * Gets the start date of the accident as a Date object.
     * @return The Date object representing the start date of the accident.
     */
    public Date getStartDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
	
		/**
 		* Treenode in the binary search tree for accident reports.
 		*/
		class TreeNode {
			AccidentReport data;
			int leftChildrenCount;
			int rightChildrenCount;
			TreeNode left;
			TreeNode right;
			
		/**
		* Constructs a TreeNode object with the provided accident report data.
		*
		* @param data The accident report data.
		*/
		public TreeNode(AccidentReport data) {
			this.data = data;
        	this.leftChildrenCount = 0;
        	this.rightChildrenCount = 0;
        	this.left = null;
        	this.right = null;
    }
}
/**
*	Binary Search Tree (BST) for storing accident reports.
*/
class BST {
    private TreeNode root;

    public BST() {
        this.root = null;
    }
    /**
     * Inserts an accident report into the BST.
     * @param data The accident report data to be inserted.
     */
    public void insert(AccidentReport data) {
        root = insertRecursive(root, data);
    }

    private TreeNode insertRecursive(TreeNode root, AccidentReport data) {
        if (root == null) {
            return new TreeNode(data);
        }

        if (data.getStartDate().compareTo(root.data.getStartDate()) < 0) {
            root.left = insertRecursive(root.left, data);
            root.leftChildrenCount++;
        } else {
            root.right = insertRecursive(root.right, data);
            root.rightChildrenCount++;
        }

        return root;
    }
    /**
     * Counts the number of reports after a given date in the BST.
     * @param date The date to compare against.
     * @return The number of reports after the given date.
     */
    public int countReportsAfterDate(Date date) {
        return countReportsAfterDateRecursive(root, date);
    }

    private int countReportsAfterDateRecursive(TreeNode root, Date date) {
        if (root == null) {
            return 0;
        }

        if (date.compareTo(root.data.getStartDate()) < 0) {
            return 1 + root.rightChildrenCount + countReportsAfterDateRecursive(root.left, date);
        } else {
            return countReportsAfterDateRecursive(root.right, date);
        }
    }
}
/**
 * Main program to read accident reports from a CSV file, build BSTs for IL and CA,
 * and provide the count of reports after a given date for a specified state.
 */
public class Program5 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        BST ILTree = new BST();
        BST CATree = new BST();

        try {
            Scanner scanner = new Scanner(new File("accidents.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                AccidentReport report = new AccidentReport(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], fields[12], fields[13]);
                if ("IL".equals(report.state)) {
                    ILTree.insert(report);
                } else if ("CA".equals(report.state)) {
                    CATree.insert(report);
                }
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime; 
            System.out.println("Time taken to build the binary search trees: " + elapsedTime + " milliseconds");

            try (Scanner scanner1 = new Scanner(System.in)) {
                System.out.println("Enter state (IL/CA) and date (yyyy-MM-dd):");
                String state = scanner1.next();
                String dateString = scanner1.next();

                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                int count = 0;

                if ("IL".equals(state)) {
                    count = ILTree.countReportsAfterDate(date);
                } else if ("CA".equals(state)) {
                    count = CATree.countReportsAfterDate(date);
                }

                System.out.println("Number of reports/accidents on and after " + dateString + " in " + state + ": " + count);
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}