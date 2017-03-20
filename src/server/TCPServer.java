package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer extends Thread {
	//port for listen to, same as client port
	public final static int PORT = 3000;
	//timeout in seconds for the server socket
	public final static int TIME_OUT_SECONDS = 60;
	//path where are all the server files
	public final static String PATH = "./data/server";
	//server socket that creates a socket per client
	private ServerSocket serverSocket;

	/**
	 * Constructor of the class - sets a timeout for the server socket, after 10 secs the throws an exception
	 * @throws IOException
	 */
	public TCPServer () throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(TIME_OUT_SECONDS*1000);
	}
	/**
	 * Method that display the files to the client
	 * @param files
	 * @throws IOException 
	 */
	public static void viewFiles(ArrayList<String> files, DataOutputStream out) throws IOException{
		String msg="";
		for (int i = 0; i < files.size(); i++) {
			msg+=(i+" "+ files.get(i))+'\n';
		}
		out.writeBytes(msg);
	}
	/**
	 * Returns an ArrayList<String> with the names of the files contained in the folder path
	 * @param path
	 * @return
	 */
	public static ArrayList<String> getFiles(String path){
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> files= new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()){
				files.add(listOfFiles[i].getName());
			}
		}
		return files;
	}
	/**
	 * Run method for the server thread
	 */
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
				
				ArrayList<String> files = getFiles(PATH);
				viewFiles(files, outToClient);
				int iFile=inFromClient.read();
				System.out.println("FROM CLIENT: "+ iFile+ " "+ files.get(iFile));
				
				
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
	/**
	 * Main method that initialize one TCPServer for all the clients
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread t = new TCPServer ();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}