package server;

import java.util.Map;

//ConcreteCommand
public class GetCommand implements Command {

    @Override
    public Response execute(Request request, Map<String, String> base) {
        try {
            Response response = new Response();
            String key = request.getKey();
            if (!base.containsKey(key)) {
                response.setResponse("ERROR");
                response.setReason("No such key");
            } else {
                response.setResponse("OK");
                response.setValue(base.get(key));
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}