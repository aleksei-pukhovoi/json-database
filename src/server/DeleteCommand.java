package server;

import java.util.Map;

//ConcreteCommand
public class DeleteCommand implements Command {
    @Override
    public Response execute(Request request, Map<String, String> base) {
        try {
            Response response = new Response();
            String key = request.getKey();
            if (base.remove(key) != null) {
                    response.setResponse("OK");
            } else {
                response.setResponse("ERROR");
                response.setReason("No such key");
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}