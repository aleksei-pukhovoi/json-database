package server;

import java.util.Map;

//Invoker
public class CommandInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Response performRequest(Request request, Map<String, String> base) {
       return command.execute(request, base);
    }
}
