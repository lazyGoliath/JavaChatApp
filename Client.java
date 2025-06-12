import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client{

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Connecting to server...");
            socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connection Established.");

            //I/O
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void startReading(){
        Runnable r1 = () -> {
            while(true){
                try {
                    String msg = br.readLine();
                    if(msg.equals("bye")){
                        System.out.println("Server terminated the chat.");
                        break;
                    }
                    System.out.println("Server: " + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2 = () -> {
            while(true){
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is client..Client is running.");

        new Client();
    }
}