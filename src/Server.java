

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author biankatpas
 */

class Echo extends Thread {
    protected Socket socket;

    public Echo(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        byte[] bytes = new byte[1024*1024];
        
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        try {
        int count;
        FileOutputStream fos = new FileOutputStream("arquivo.txt");

        while ((count = inp.read(bytes)) > 0) {
            System.out.println("Thread Aceita");
          fos.write(bytes);
        }
        } catch (IOException e) {
            return;
        }
    }
}

public class Server {

    static final int PORT = 1234;

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't setup server on this port number. ");
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println(".");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new Echo(socket).start();
        }
    }
}