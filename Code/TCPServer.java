import java.net.*;
import java.io.*;

// Name: Param Jansari
// Student Number: 6046833

public class TCPServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");  //only port number required to identify connection
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]); // get port number
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try{
          serverSocket =
              new ServerSocket(Integer.parseInt(args[0])); //server socket created on provided port
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        while(true){
          try{
            clientSocket = serverSocket.accept(); // accepts a client connect
          }catch(IOException e){
            System.out.println("I/O error: " + e);
          }
          new WorkerThread(clientSocket).start(); // dedicates a thread to the client
        }
    }
}
