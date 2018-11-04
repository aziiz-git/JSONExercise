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
			System.out.print("Enter Course Name: ");
			String course = input.nextLine(); // nextLine() reads the remainder of the current line even if it is empty.

			// Check to see if User hit just Enter
			if (course.length() == 0) {
				break;
			}

			// Get the Grade
			System.out.print("Enter Grade: ");
			int grade = input.nextInt();

			if (input.hasNextLine()) { // Why? nextInt() reads an integer but does not read the escape sequence "\n".
										// The 'Enter' that we press is still in the input buffer. If this if loop not
										// present, it'll exit 'while' loop after first data set entry
				input.nextLine();
			}

			// Create a JSON Object and Array AND Store a Class Object in it
			JSONObject courseObject = new JSONObject();
			courseObject.put("grade", grade);
			courseObject.put("course", course);

			// Add the course to the Array
			courses.add(courseObject);

		}

		// Add the Array to the root object
		root.put("courses", courses);

		System.out.println(root.toJSONString());

		// Create a file and write JSON structure to it
		File file = new File("StudentGrades.txt"); // The file will be overwritten

		/*
		 * try { // This won't work :) PrintWriter writer = new PrintWriter(file);
		 * writer.print(root.toJSONString()); } catch (FileNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

		try (PrintWriter writer = new PrintWriter(file)) { // Try with resources
			writer.print(root.toJSONString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("File created successfully!\n\n Hit return to display");
		input.nextLine(); // NL

		try {
			input = new Scanner(file);  // input was already declared and initialized before. So just reinitialized.
			StringBuilder jsonIn = new StringBuilder();  // Why? 'String' does not allow appending. Each method you invoke on a String creates a new object and returns it. This is because String is immutable - it cannot change its internal state.
			while (input.hasNextLine()) {  // We may have many records
				jsonIn.append(input.nextLine());
			}

			System.out.println(jsonIn.toString());
			
			JSONParser parser = new JSONParser();
			
			JSONObject objRoot = (JSONObject) parser.parse(jsonIn.toString());
			
			System.out.printf("Student name is %s\n", objRoot.get("name").toString());
			
			JSONArray coursesIn =  (JSONArray) objRoot.get("courses");
			
			for(int i = 0 ; i < coursesIn.size() ; i++) {
				JSONObject courseIn = (JSONObject) coursesIn.get(i);
				long gradeIn = (long) courseIn.get("grade");
				String nameIn = (String) courseIn.get("name");
				System.out.printf("Course %s | Grade %d\n ", nameIn, gradeIn);				
			}		
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
