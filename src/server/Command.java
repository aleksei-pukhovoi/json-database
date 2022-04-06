package server;

import java.util.Map;

public interface Command {
    Response execute(Request request, Map<String, String> base);
}
