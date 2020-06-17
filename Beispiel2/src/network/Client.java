package network;

import java.io.IOException;
import container.Container;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client extends Container<String> implements Runnable {
	private int outPort;
	private String name;
	private Socket outSocket;
	private Scanner scanner;
	private PrintWriter writer;
	private InputStreamReader inputReader;
	private BufferedReader bufferedReader;
	private boolean running = false;

	public Client (String name, int outPort) {
		this.name = name;
		this.outPort = outPort;
		this.scanner = new Scanner(System.in);
	}
	
	private void start () throws IOException {
		System.out.println("Starting the " + this.name + "...");
		this.outSocket = new Socket("localhost", this.outPort);
		this.writer = new PrintWriter(this.outSocket.getOutputStream());
		this.inputReader = new InputStreamReader(this.outSocket.getInputStream());
		this.bufferedReader = new BufferedReader(inputReader);
		this.running = true;
	}

	public void run () {
		try {
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean done = false;
		String data;
		while (!done) {
			System.out.println("0 - Exit");
			System.out.println("1 - Add an element");
			System.out.println("2 - Remove an element");
			System.out.println("3 - Print the container");
			System.out.println("4 - Identify");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 0:
					this.write("exit");
					try {
						this.stop();
					} catch (IOException e) {
						e.printStackTrace();
					}
					done = true;
					break;
				case 1:
					System.out.println("Please enter the value of the element to be added:");
					data = scanner.nextLine();
					super.add(data);
					this.write("add", data);
					break;
				case 2:
					System.out.println("Please enter the value of the element to be removed:");
					data = scanner.nextLine();
					super.remove(data);
					this.write("remove", data);
					break;
				case 3:
					System.out.println(super.toString());
					this.write("print");
					break;
				case 4:
					System.out.println("My name is " + this.name);
					break;
				default:
					System.out.println("Your input was not valid!");
					break;
			}
		}
	}
	
	private void stop () throws IOException {
		System.out.println("Stopping the " + this.name + "...");
		if (!this.running) {
			System.out.println("The client is not running. Please start the client first.");
			return;
		}
		this.scanner.close();
		this.outSocket.close();
		this.running = false;
	}
	
	private void write (String command) {
		writer.println(command);
		writer.flush();
	}
	
	// Set to public to test manually.
	public void write (String command, String data) {
		writer.println(command);
		writer.flush();
		writer.println(data);
		writer.flush();
		try {
			System.out.println(bufferedReader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
