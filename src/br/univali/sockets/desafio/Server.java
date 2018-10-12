package br.univali.sockets.desafio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author biankatpas
 */

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