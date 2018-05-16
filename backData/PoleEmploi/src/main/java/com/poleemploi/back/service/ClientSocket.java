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

	public ClientSocket(Socket pSock) {
		sock = pSock;
	}
	
	public void addAlertToSend(JSONObject alert){
		toSendAlerts.add(alert);
	}

	public void run() {

		boolean closeConnexion = false;
		while (!sock.isClosed()) {

			try {
				JSONObject toSend;
				writer = new OutputStreamWriter(sock.getOutputStream(),StandardCharsets.UTF_8);
				
				toSend = toSendAlerts.get(0);
				toSendAlerts.remove(toSend);

				writer.write(toSend.toString());
				writer.flush();

				if (closeConnexion) {
					writer = null;
					sock.close();
					break;
				}
			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
