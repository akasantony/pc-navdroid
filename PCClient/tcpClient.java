import java.net.*;
import java.io.*;
import java.util.*;

public class tcpClient{
   public static void main(String [] args){
      String serverName = "192.168.2.8";
      int port = Integer.parseInt(args[0]);
      while(true){
      try{
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket("192.168.2.8", port);

         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

         Scanner reader = new Scanner(System.in);
         System.out.println("Enter X:");

         int x = reader.nextInt();   

         System.out.println("Enter y:");

         int y = reader.nextInt();     

         String coordinates = Integer.toString(x) + ':' + Integer.toString(y);

         out.writeUTF(coordinates);
         
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      }catch(IOException e){
         e.printStackTrace();
      }
   }
   }
}