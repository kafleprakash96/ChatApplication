package com.chatapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket server;
	Socket socket;

	// Create variable for input stream and output stream

	BufferedReader br;
	PrintWriter pw;

	public Server() {
		try {
			server = new ServerSocket(7777);
			System.out.println("Server is ready for connection");
			System.out.println("Waiting...");

			socket = server.accept(); // accept from client

			// Get the request and store to input stream
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// InputStreamReader will convert the byte to character and is passed to br

			// do same for output stream
			pw = new PrintWriter(socket.getOutputStream());

			/*
			 * Once the request is ready for read and write, Create method to start reading
			 * and writing
			 */

			startReading();
			startWriting();

		} catch (Exception e) {

		}

	}

	public void startReading() {

		// Create thread that will read the data
		Runnable r1 = () -> {

			System.out.println("Reader started...");

			try {

				while (true) {

					// Store the received message in string
					String msg = br.readLine();
					if (msg.equals("bye")) {
						System.out.println("Client terminated the chat...");
						socket.close();
						break;
					}
					System.out.println("Client: " + msg);

				}
			} catch (Exception e) {

			}

		};

		// Start the thread
		new Thread(r1).start();

	}

	public void startWriting() {

		// Create thread that will get the data from user and send to client
		Runnable r2 = () -> {

			System.out.println("Writer started...");
			try {
				while (true) {

					// Get the data from the user through the console
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();

					// print to the client
					pw.println(content);
					pw.flush();
				}
			} catch (Exception e) {

			}

		};

		// Start the thread
		new Thread(r2).start();

	}

	public static void main(String[] args) {
		new Server();
	}
}

