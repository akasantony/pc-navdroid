import java.net.*;
import java.io.*;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class tcpServer extends Thread
{
   private ServerSocket serverSocket;
   private int xCord, yCord;
   
   // Open server socket on given port address
   public tcpServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
    //  serverSocket.setSoTimeout(300000000);
   }

   public void run()
   {
      while(true)
      {
         try
         {  

            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            
            //Accept incoming coonection
            Socket server = serverSocket.accept();

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            //Get input stream of socket and read data from the socket in UTF format
            DataInputStream in = new DataInputStream(server.getInputStream());
            String str = in.readUTF();

            //Parse and print incoming data
            System.out.println(str);
            String cord[] = str.split(":");
            System.out.println(cord[0]);

            //Call setMouse function and pass the coordinates as parameters
            //Print the coordinates
            setMouse(cord[0],cord[1]);
            System.out.println(cord[0]+"--"+cord[1]);
            
            //Get output stream of socket and print message on receiving data from port in UTF format
            //Close server port                   
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         
         } catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch(IOException e) {
            e.printStackTrace();
            break;
         }catch(Exception e) {
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      
      int port = 3006;
      
      //Start a thread with tcpServer
      try
      {
         Thread t = new tcpServer(port);
         t.start();
      }catch(IOException e){
         e.printStackTrace();
      }
   }
   
   //setMouse function uses Robot class. Takes in coordinates as parameters
   public static void setMouse(String xCord, String yCord) throws Exception{
      Robot rbt = new Robot();

      //If xCord is L then simulate left click
      if(xCord.equals("L")){
         System.out.println("If working");
         rbt.mousePress(InputEvent.BUTTON1_MASK);
         rbt.mouseRelease(InputEvent.BUTTON1_MASK);
         System.out.println(xCord);
         return;

      }

      //If xCord is R then simulate right click
      if(xCord.equals("R")){
         rbt.mousePress(InputEvent.BUTTON3_MASK);
         rbt.mouseRelease(InputEvent.BUTTON3_MASK);
         System.out.println(xCord);
         return;

      }

      //If xCord and yCord are integers then move mouse position
      else{
         int x = Integer.parseInt(xCord);
         int y = Integer.parseInt(yCord);

         rbt.mouseMove(x,y);
         System.out.println(x+"-"+y);
         return;
      } 

      }
}
