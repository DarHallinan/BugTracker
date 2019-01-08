package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

/**
 * 
 * @author G00342845
 * 		   Darren Hallinan
 *
 */

public class Employee {
	static String message;
	// employee variables
	private String name;
	private String empId;
	private String email;
	private String department;

	// basic constructor
	public Employee(String name, String empId, String email, String department) {
		this.name = name;
		this.empId = empId;
		this.email = email;
		this.department = department;
	}

	// name getter/setter
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	// ID getter/setter
	public String getEmpId() { return empId; }
	public void setEmpId(String empId) { this.empId = empId; }
	
	// email getter/setter
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	// department getter/setter
	public String getDepartment() { return department; }
	public void setDepartment(String department) { this.department = department; }
	
	/** signup function for the server */
	public static void signup(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// user variables
		String name;
		String empId;
		String email;
		String department;
		
		// create a loop that gives user the choice to re-enter incorrect info
		do {
			// gives user menu to enter details
			ConnectHandler.sendMessage("Please enter your name: "); // send prompt to enter name
			name = in.readObject().toString();
			ConnectHandler.sendMessage("Please enter your employee ID: "); // send prompt to enter ID
			empId = in.readObject().toString();
			ConnectHandler.sendMessage("Please enter your email address: "); // send prompt to enter email
			email = in.readObject().toString();
			ConnectHandler.sendMessage("Please enter your department: "); // send prompt to enter password
			department = in.readObject().toString();
			
			ConnectHandler.sendMessage("Press 1 to confirm details or 2 to re-enter: \n");
			message = in.readObject().toString();
		}while(!message.equalsIgnoreCase("1"));
		
		// creates employee object
		Employee employee = new Employee(name, empId, email, department);

		// writes to file only after details are confirmed
		FileWriter fW = new FileWriter("Resources\\userInfo.txt", true); // set append to true
		fW.write(name + " " + empId + " " + email + " " + department + "\n");
		fW.close();
	} // end of sign up
	
	/** login function for the server */
	public static void login(ObjectInputStream in) throws ClassNotFoundException, IOException {
		File file = new File("Resources\\userInfo.txt");
		Scanner scanner = new Scanner(file);
		// only need ID and email for login
		String empId;
		String email;

		ConnectHandler.sendMessage("Please enter your ID: "); // send prompt to enter ID
		empId = in.readObject().toString();
		ConnectHandler.sendMessage("Please enter your email address: "); // send prompt to enter email
		email = in.readObject().toString();

		String searchStr = (empId.toString() + " " + email.toString());

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] details = line.split(" ");
			String existingId = details[0];
			String existingEmail = details[1];

			if (searchStr == (existingId + existingEmail)) {
				ConnectHandler.sendMessage("Welcome Back " + empId);
				ConnectHandler.bugMenu();				
				break;
			} else {
				ConnectHandler.sendMessage("Sorry... ID of " + empId + " was not found");
				break;
			}
		}
		ConnectHandler.bugMenu(); // could not get login working, had to test bug registration

	} // end of login
	
	
} // end of Employee
