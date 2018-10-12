package br.univali.sockets.desafio;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 
 * @author biankatpas
 */

public class Echo extends Thread {
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