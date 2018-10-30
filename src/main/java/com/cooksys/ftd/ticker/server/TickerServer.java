package com.cooksys.ftd.ticker.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TickerServer {
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(4000);) {
			while (true) {
				new Thread(new ClientHandler(server.accept())).start();
				System.out.println("Client connected");
			}
		} catch (IOException e) {
			System.out.println("Client disconnected.");
		}
	}
}