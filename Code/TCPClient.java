import java.io.*;
import java.net.*;


// Name: Param Jansari
// Student Number: 6046833

public class TCPClient {
  private final static String password = "secret password"; // secret key to encrypt and decrypt messages

  public static void main(String[] args) {
    try{

      Socket server = new Socket("localhost", 4000); // connect to server

      //set up inputs and outputs
      PrintWriter out = new PrintWriter(server.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
      BufferedReader termIn = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Connection Established!");
      String userInput;

      // Talk to server
      while ((userInput = termIn.readLine()) != null ){

        String serverResponse = "bye";

        // processes input/output differently based on if encryption is enabled
        if(AESEncryption.enableEncryption){ 
          // will encrypt communication channel with AES encryption
          String encryptedUserInput = AESEncryption.encryptMessage(userInput, password);
          out.println(encryptedUserInput);
          String encryptedServerResponse = in.readLine();
          serverResponse = AESEncryption.decryptMessage(encryptedServerResponse, password);
          System.out.println("Server: " + serverResponse); //Server response  
        }else{
          out.println(userInput);
          serverResponse = in.readLine();
          System.out.println("Server: " + serverResponse); //Server response
        }

        //breaks when server replies with "bye"
        if(serverResponse.equals("bye")){
          out.close();
          in.close();
          termIn.close();
          server.close();
          System.out.println("Connection Terminated");
          break;
        }
      }

    }catch(UnknownHostException e){
        System.err.println("Who's the host??");
    }catch(IOException e){
        System.err.println("No I/O!");
    }finally{
      System.exit(1);
    }
  }
}
