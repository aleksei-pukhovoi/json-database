package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        DataBase base = new DataBase();
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    String receivedMsg = input.readUTF();
                    Gson gson = new GsonBuilder().create();
                    Request request = gson.fromJson(receivedMsg, Request.class);
                    Response response;
                    if (base.isExit(request.getType())) {
                        response = base.getExitResponse();
                        output.writeUTF(gson.toJson(response));
                        break;
                    }
                    response = base.getResponse(request);
                    String sentMsg = gson.toJson(response);
                    output.writeUTF(sentMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
