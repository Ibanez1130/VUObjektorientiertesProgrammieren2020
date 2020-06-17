package network;

import container.Container;
import java.net.*;
import java.io.*;

public class Server extends Container<String> implements Runnable {
	private int inPort;
	private int outPort;
	private String name;
	private ServerSocket inSocket;
	private Socket outSocket;
	private Socket clientSocket;
	private PrintWriter writer;
	private PrintWriter clientWriter;
	private InputStreamReader inputReader;
	private BufferedReader bufferedReader;
	private boolean running = false;

	public Server (String name, int inPort) {
		this.name = name;
		this.inPort = inPort;
	}
	
	public Server (String name, int inPort, int outPort) {
		this.name = name;
		this.inPort = inPort;
		this.outPort = outPort;
	}
	
	private void start () throws IOException {
		System.out.println("Starting the " + this.name + "...");
		this.inSocket = new ServerSocket(this.inPort);
		this.outSocket = this.inSocket.accept();
		this.writer = new PrintWriter(this.outSocket.getOutputStream());
		this.inputReader = new InputStreamReader(this.outSocket.getInputStream());
		this.bufferedReader = new BufferedReader(inputReader);
		this.running = true;
		if (this.outPort != 0) {
			this.clientSocket = new Socket("localhost", this.outPort);
			this.clientWriter = new PrintWriter(this.clientSocket.getOutputStream());
		}
	}

	public void run () {
		try {
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean done = false;
		try {
			while (!done) {
				String operation = bufferedReader.readLine();
				String data;
				boolean success = false;
				switch (operation) {
				case "exit":
					done = true;
					try {
						this.sendToClients(operation);
						this.stop();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "add":
					data = bufferedReader.readLine();
					success = super.add(data);
					try {
						this.sendToClients(operation, data);
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.write((success) ? ("Element added successfully.") : ("Element could not be added."));
					break;
				case "remove":
					data = bufferedReader.readLine();
					success = super.remove(data);
					try {
						this.sendToClients(operation, data);
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.write((success) ? ("Element removed successfully.") : ("Element could not be removed."));
					break;
				case "print":
					System.out.println(this.name + ": " + super.toString());
					try {
						this.sendToClients(operation);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void stop () throws IOException {
		System.out.println("Stopping the " + this.name + "...");
		if (!this.running) {
			System.out.println("The server is not running. Please start the server first.");
			return;
		}
		this.inSocket.close();
		this.outSocket.close();
		this.running = false;
	}
	
	private void sendToClients (String operation) throws IOException {
		if (this.clientSocket == null) return;
		this.clientWriter.println(operation);
		this.clientWriter.flush();
	}
	
	private void sendToClients (String operation, String data) throws IOException {
		if (this.clientSocket == null) return;
		this.clientWriter.println(operation);
		this.clientWriter.flush();
		this.clientWriter.println(data);
		this.clientWriter.flush();
		InputStreamReader inputReader = new InputStreamReader(this.clientSocket.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		System.out.println(bufferedReader.readLine());
	}
	
	private void write (String text) {
		this.writer.println("[" + this.name + "] " + text);
		this.writer.flush();
	}
}
