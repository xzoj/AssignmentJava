/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import entity.Champion;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author honghung
 */
public class GameServer {

    private static ArrayList<ClientThread> listClient;

    public static void main(String[] args) throws IOException {
        listClient = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Khởi động server thành công, chờ client kết nối.");
        while (true) {
            ClientThread ct = new ClientThread(serverSocket.accept());
            listClient.add(ct);
            ct.start();
            System.out.println("Ket noi thanh cong!");
        }
//        socket.getOutputStream(); // tương ứng với inputstream ở client.
    }

    public static void publicMessage(String message) {
        try {
            for (ClientThread clientThread : listClient) {
                if (clientThread.getSocket().isConnected()) {
                    clientThread.getBw().write(message);
                    clientThread.getBw().newLine();
                    clientThread.getBw().flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    
     public static void publicObject(Champion bird) {
        try {
            for (ClientThread clientThread : listClient) {
                if (clientThread.getSocket().isConnected()) {
                    clientThread.getOos().writeObject(bird);
                    clientThread.getOos().flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
