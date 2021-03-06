package com.poleemploi.back.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SocketTransfer extends Thread {

	private int port = 8181;
	private ServerSocket server = null;
	private boolean isRunning = true;
	private List<ClientSocket> clientList =new  ArrayList<ClientSocket>();

	public SocketTransfer() {
		try {
			server = new ServerSocket(port);
			open();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {

		Thread t = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {
					clientList = clientList.stream()//
							.filter(socket -> !socket.isFinish())//
							.collect(Collectors.toList());
					try {
						Socket client = server.accept();
						ClientSocket clientSock = new ClientSocket(client);
						clientList.add(clientSock);
						Thread t = new Thread(clientSock);
						t.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		});

		t.start();
	}
	
	public void sendNotification(JSONObject alert){
		clientList.forEach(client -> client.addAlertToSend(alert));
	}

	public void close() {
		isRunning = false;
	}
}
