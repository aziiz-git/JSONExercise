import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONExercise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter Student Name: ");		
		String name = input.nextLine();
		
		System.out.print("Enter Course Name: ");
		
		String course1 = input.nextLine();
		
		System.out.print("Enter Grade: ");
		
		int grade1 = input.nextInt();
		
		// Create new JSON Object
		JSONObject root = new JSONObject();
		
		// Put the name name-value pair
		root.put("name", name);		
		
		// Create a JSON Object and Array AND Store a Class Object in it 
		JSONObject courseObject1 = new JSONObject();
		courseObject1.put("grade", grade1);
		courseObject1.put("course", course1);
		
		JSONArray courses = new JSONArray();
		courses.add(courseObject1);  // Storing Class Objects
		
		// Add the Array to the root object
		root.put("courses", courses);
		
		System.out.println(root.toJSONString());		
		
	}

}
