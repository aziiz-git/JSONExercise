import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONExercise1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);

		System.out.print("Enter Student Name: ");
		String name = input.nextLine();

		// Create new JSON Object // FIRST THIS unlike the first one
		JSONObject root = new JSONObject();

		// Put the name name-value pair
		root.put("name", name);

		JSONArray courses = new JSONArray(); // JSON Array initialization moved here

		while (true) {

			// Get the Course Name
			System.out.print("Enter Course: ");
			String course = input.nextLine(); // nextLine() reads the remainder of the current line even if it is empty.

			// Check to see if User hit just Enter
			if (course.length() == 0) {
				break;
			}

			// Get the Grade
			System.out.print("Enter Grade: ");
			int grade = input.nextInt();

			if (input.hasNextLine()) { // Why? 'nextInt()' reads an integer but does not read the escape sequence "\n".
										// The 'Enter' that we press is still in the input buffer. If this 'if loop' not
										// present, it'll exit 'while' loop after first data set entry
				input.nextLine();
			}

			// Create a JSON Object and Array AND Store a Class Object in it
			JSONObject courseObject = new JSONObject();
			courseObject.put("course", course);
			courseObject.put("grade", grade);

			// Add the course to the Array
			courses.add(courseObject);

		}

		// Add the Array to the root object
		root.put("courses", courses);

		System.out.println("\n" + root.toJSONString());

		System.out.println("\n(Above) Printing directly from the Console input.");

		// Create a file and write JSON structure to it
		File file = new File("StudentGrades.txt"); // The file will be overwritten

		/*
		 * try { // This won't work :) PrintWriter writer = new PrintWriter(file);
		 * writer.print(root.toJSONString()); } catch (FileNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

		try (PrintWriter writer = new PrintWriter(file)) { // Try with resources
			writer.print(root.toJSONString()); // Write into the file
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\nFile created successfully :) Hit return to display!");
		input.nextLine(); // Read NL from User

		try {
			input = new Scanner(file); // input was already declared and initialized before. So just reinitialized.
			StringBuilder jsonInFile = new StringBuilder(); // Why? 'String' does not allow appending. Each method you
															// invoke on a String creates a new object and returns it.
															// This is because String is immutable - it cannot change
															// its internal state.
			while (input.hasNextLine()) { // We may have many records
				jsonInFile.append(input.nextLine());
			}

			System.out.println(jsonInFile.toString());

			System.out.println("\n(Above) Printed from the file. But, not parsed.\n");

			JSONParser parser = new JSONParser(); // Parsing

			JSONObject rootObjInFile = (JSONObject) parser.parse(jsonInFile.toString()); // This is just the 'root'

			System.out.printf("Student name is %s\n", rootObjInFile.get("name").toString());

			
/*			 System.out.printf("Student name is %s\n",
			 objRootInFile.get("course").toString()); // These will not work here.
		  
			 System.out.printf("Student name is %s\n",
			 objRootInFile.get("grade").toString());*/
			 

			System.out.println("\n(Above) Student name, that is, only the Root node is printed.\n");

			JSONArray coursesInFile = (JSONArray) rootObjInFile.get("courses"); // We need an Array for the objects

			for (int i = 0; i < coursesInFile.size(); i++) {
				JSONObject courseObjIn = (JSONObject) coursesInFile.get(i);  // Will go record by record
				String courseIn = (String) courseObjIn.get("course");
				long gradeIn = (long) courseObjIn.get("grade");
				System.out.printf("Course %s :::: Grade %d\n", courseIn, gradeIn);
			}
			
			System.out.println("\nParsed data above!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}