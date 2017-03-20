package client;


import java.io.*;
import java.net.*;
public class TCPClient {
	public static final String IP="192.168.56.1";
	public static final int PORT=3000;

	public static void main(String[] args)throws Exception {
		String sentence, modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket(IP, PORT);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		sentence = inFromUser.readLine();
		
		outToServer.writeBytes(sentence+ '\n');
		
		modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: "+ modifiedSentence);
		clientSocket.close();
	}
}
