import java.net.*;
import java.io.*;

// Name: Param Jansari
// Student Number: 6046833

public class WorkerThread extends Thread {
  protected Socket client = null;
  protected static boolean enableEncryption = true;
  protected final static String password = "secret password"; // secret key to encrypt and decrypt messages

  public WorkerThread(Socket client){
    this.client = client;
  }

   public void run(){
     try{
      
      PrintWriter out = new PrintWriter(client.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));  //takes input from client
      String inputLine;
      
      while ((inputLine = in.readLine()) != null) {
        String clientMessage = "quit";

        // decrypt message is encryption is enabled
        if(AESEncryption.enableEncryption){
          String decryptedClientMessage = AESEncryption.decryptMessage(inputLine, password); // decrypt client message
          clientMessage = decryptedClientMessage; 
        }else{
          clientMessage = inputLine;
        }

        if(clientMessage.equalsIgnoreCase("quit")){ // replies with bye and breaks if client says quit
          System.out.println("Client[" + client.getPort() + "] has left");
          if(AESEncryption.enableEncryption){
            out.println(AESEncryption.encryptMessage("bye", password));
          }else{
            out.println("bye");
          }
          break;
        }

        if (AESEncryption.enableEncryption){ 
          // will encrypt communication channel with AES encryption
          System.out.println("Client[" + client.getPort() + "] said: " + clientMessage);
          String responseMessage = clientMessage.toUpperCase() + "      port: " + client.getPort(); // repeats what client said in all caps and port number
          String encryptedResponseMessage = AESEncryption.encryptMessage(responseMessage, password); // encrypt server reply
          out.println(encryptedResponseMessage); 
        }else{
          System.out.println("Client[" + client.getPort() + "] said: " + clientMessage);
          out.println(clientMessage.toUpperCase() + "      port: " + client.getPort()); // repeats what client said in all caps and port number
        }

      }
      //closes resources
      out.close();
      in.close();
      client.close();
    }catch(IOException e){
    System.out.println("IO exception");
    e.printStackTrace();
    }
  }
}
