package server;


import java.io.*;
import java.net.*;
public class TCPServer {
	public final static int PORT = 6789;
	public static void main(String[] args) throws Exception {
		String clientSentece, capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(PORT);
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentece = inFromClient.readLine();
			capitalizedSentence = clientSentece.toUpperCase()+ '\n';
			outToClient.writeBytes(capitalizedSentence);
			
		}
	}
}
