package com.chatapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	Socket socket;

	BufferedReader br;
	PrintWriter pw;

	public Client() {

		try {

			System.out.println("Sending request to server");
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("Connection done");

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());

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
						System.out.println("Server terminated the chat...");
						break;
					}
					System.out.println("Server: " + msg);

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
		new Client();
	}

}

