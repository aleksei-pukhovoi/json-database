package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {

    @Parameter(names = "-t")
    String type;
    @Parameter(names = "-k")
    String key;
    @Parameter(names = "-v")
    String value;
    @Parameter(names = "-in")
    String fileNameRequest;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public void run() {
        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Client started!");
            Gson gson = new GsonBuilder().create();

            String sentMsg = gson.toJson(getRequest(gson));
            output.writeUTF(sentMsg);

            System.out.println("Sent: " + sentMsg);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request getRequest(Gson gson) {
        if (Objects.isNull(fileNameRequest)) {
            return new Request(type, key, value);
        }
        else {
            String fileData = getJsonData();
            return gson.fromJson(fileData, Request.class);
        }
    }

    private String getJsonData(){
        String jsonData = "";
        try {
            jsonData = new String(Files.readAllBytes(Paths.get("src/client/data/" + fileNameRequest)));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        return jsonData;
    }
}
