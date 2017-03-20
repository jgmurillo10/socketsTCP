package server;


import java.io.*;
import java.net.*;

public class TCPServer extends Thread {
	public final static int PORT = 3000;
	public final static int TIME_OUT_SECONDS = 60;
	private ServerSocket serverSocket;

	public TCPServer () throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(TIME_OUT_SECONDS*1000);    

	}

	public void run() {
		String clientSentence, capitalizedSentence;
		while (true) {
			try {
				
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket client = serverSocket.accept();

				System.out.println("Just connected to " + client.getRemoteSocketAddress());
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());
				clientSentence = inFromClient.readLine();
				capitalizedSentence = clientSentence.toUpperCase()+ '\n';
				outToClient.writeBytes(capitalizedSentence);
				client.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		try {
			Thread t = new TCPServer ();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}