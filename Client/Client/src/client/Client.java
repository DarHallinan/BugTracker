package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

/**
 * 
 * @author G00342845
 * 		   Darren Hallinan
 *
 */

/** Client() class */
public class Client {
	// initialise variables for Client
	private Socket connection;
	private Scanner console;
	private String ipaddress;
	private int portaddress;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;

	/** constructor method for the program's client */
	public Client() {
	  console = new Scanner(System.in);
	  
	  System.out.println("Enter the IP Address of the server"); 
	  ipaddress = console.nextLine();
	  
	  System.out.println("Enter the TCP Port"); 
	  portaddress = console.nextInt();
	 

		//ipaddress = "127.0.0.1";
		//portaddress = 5005;
	}
	
	/** main method for the program's client */
	public static void main(String[] args) throws IOException {
		Client temp = new Client();
		temp.clientApp();
	}
	
	/** message sending method to the server */
	void sendMessage(String msg){
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) { ioException.printStackTrace(); }
	}

	/** Clientapp method which does most of the client's work */
	public void clientApp() throws IOException {
		try {
			connection = new Socket(ipaddress, portaddress);
			
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client ready to communicate");

			// loop to keep connection open while user doesn't choose to exit
			do {
				message = in.readObject().toString();
				System.out.println(message);
				message = console.next();
				sendMessage(message);
			}while(!message.equalsIgnoreCase("-1"));
			
			// closes down communication with the server
			out.close();
			in.close();
			connection.close();
		}
		catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
		
	}


} // end of Client()
