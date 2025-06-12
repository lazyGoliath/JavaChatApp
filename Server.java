import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server{

    BufferedReader br;
    PrintWriter out;

    ServerSocket server;
    Socket socket;

    //Constructor
    public Server(){

        try{
            server = new ServerSocket(5000);  //read on port 5000
            System.out.println("Server is running.");
            System.out.println("Waiting for client...");
            socket = server.accept();  //accept the client connection

            //Input Stram
            //convert bytes -> char -> br object
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Output Stream
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //taking input from client; recieving from server
    public void startReading(){
        System.out.println("Reader started.");
        Runnable r1 = () ->{
            while(true){
                try{
                    String msg = br.readLine();
                    if(msg.equals("bye")){
                        System.out.println("Client terminated the chat.");
                        break;
                    }
                    System.out.println("Client: " + msg);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start();
    }

    //taking input from server; sending to client
    public void startWriting(){
        System.out.println("Writer started.");
        Runnable r2 = () ->{
            while(true){
                try{

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                } catch(Exception e){
                    e.printStackTrace();
                }       
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server..Server is running.");

        new Server();
    }
}