package server;

import beans.Product;
import database.DBAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server 
{
    public static void main(String[] args) 
    {
        try {
            new Server();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Server() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9999);
        ClientConnection clientConnection = new ClientConnection(serverSocket);
        clientConnection.start();
    }
    
    class ClientConnection extends Thread
    {
        private ServerSocket serverSocket;
        public ClientConnection(ServerSocket ss)
        {
            this.serverSocket = ss;
        }

        @Override
        public void run() 
        {
            try {
                System.out.println("Server wartet");
                Socket s = serverSocket.accept();
                System.out.println("Connected mit: "+s.getRemoteSocketAddress());
                ClientCommunication clientCommunication = new ClientCommunication(s);
                clientCommunication.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    class ClientCommunication extends Thread
    {
        private DBAccess dbAccess;
        private Socket socket;
        private ArrayList<Product> listProduct = new ArrayList<>();
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        public ClientCommunication(Socket s)
        {
            this.socket = s;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() 
        {
            try {
                dbAccess = new DBAccess();
                String line = (String)ois.readObject();
                
                if(line.contains("Anmelden"))
                {
                    String username = (String)ois.readObject();
                    String password = dbAccess.getPassword(username);
                    line = (String)ois.readObject();
                    if(password.equals(line))
                    {
                        oos.writeObject("Willkommen, "+username);
                        oos.flush();
                        
                        
                    }
                }
                else
                {
                    
                }
                
                
                listProduct = dbAccess.showAllProducts("test");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void log(String message)
    {
        System.out.println(message);
    }
}