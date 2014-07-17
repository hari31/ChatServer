import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class Server implements Runnable {

	static ServerSocket serverSocket;
	static Socket socket;

	static BufferedReader in;
	static PrintWriter out;


	
	

	Thread thread = new Thread(this);
	public static int port = 25568;

	static List<Client> clients = new ArrayList<Client>();

	public static ServerWindow window;


	public Server(){
		//port = Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
		window = new ServerWindow();
	}

	public static void main(String[] args){
		Server server = new Server();
		server.start();
	}



	public void start(){
		thread.start();
	}

	public void run() {

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started.");

			while(true){
				socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(),true);
				
				
				final Client client = new Client(socket);
				clients.add(client);
				
				String adress = socket.getInetAddress().toString();

				out.println("Welcome aboard!");
				window.writeToConsole(adress + " connected.");


				new Thread(new Runnable(){

					public void run() {
						while(true){

							for(int i = 0; i < clients.size(); i++){
								if(clients.get(i).in == null){
									clients.remove(clients.get(i));
									System.out.println("There is a disconnected client!");
								}
							}


						}

					}}).start();


				new Thread(new Runnable(){


					public void run() {
						BufferedReader savedIn = in;
						
					
						while(true){
							
							try {

								
								String input = savedIn.readLine();
								
								
								
								window.writeToConsole(input);
								if(input.startsWith("/message")){
								sendMessage(input.replace("/message", ""));
								}
								if(input.endsWith(".png")){
									sendMessage("/image"+input);
								}
								
								



							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}}).start();




			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendMessage(String message){
		for(int i = 0; i < clients.size(); i++){
			Client c = clients.get(i);
			if(c != null && c.out != null && c.in != null){
				c.out.println(message);
			}
		}
	}


}
