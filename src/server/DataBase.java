package server;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private final Map<String, String> base;
    private final CommandInvoker invoker;
    private final static String DB_PATH = "src/server/data/db.json";

    public DataBase() {
        this.base = new HashMap<>();
        this.invoker = new CommandInvoker();
    }

    public Response getResponse(Request request) {
        try {
            Command command = getCommand(request.getType());
            invoker.setCommand(command);
            return invoker.performRequest(request, base);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Command getCommand(String command) {
        switch (command) {
            case ("get"):
                return new GetCommand();
            case ("set"):
                return new SetCommand();
            case ("delete"):
                return new DeleteCommand();
        }
        throw new IllegalArgumentException("No such command");
    }

    public boolean isExit(String command) {
        return "exit".equals(command);
    }

    public Response getExitResponse() {
        Response response = new Response();
        response.setResponse("OK");
        return response;
    }
}
