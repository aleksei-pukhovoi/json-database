package server;

import java.util.Map;

//ConcreteCommand
public class SetCommand implements Command {
    @Override
    public Response execute(Request request, Map<String, String> base) {
        try {
            Response response = new Response();
            String value = request.getValue();
            String key = request.getKey();
            base.put(key, value);
            response.setResponse("OK");
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}