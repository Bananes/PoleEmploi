package com.poleemploi.back.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ClientSocket implements Runnable {

	private Socket sock;
	private OutputStreamWriter writer = null;
	private List<JSONObject> toSendAlerts = new ArrayList<>();
	private boolean finish = false;

	public ClientSocket(Socket pSock) {
		sock = pSock;
	}

	public void addAlertToSend(JSONObject alert) {
		toSendAlerts.add(alert);
	}

	public void run() {
		try {

			boolean closeConnexion = false;
			writer = new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8);
			
			while (!finish) {
				JSONObject toSend;
				if (toSendAlerts.size() > 0) {
					toSend = toSendAlerts.get(0);
					toSendAlerts.remove(0);
					writer.write(toSend.toString()+"\n");
					writer.flush();
					if (closeConnexion) {
						break;
					}
				}
			}
			sock.close();
			finish = true;
			
		} catch (SocketException e) {
			finish = true;
		} catch (IOException e) {
			finish = true;
			e.printStackTrace();
		}

	}
	
	public boolean isFinish(){
		return finish;
	}

}
