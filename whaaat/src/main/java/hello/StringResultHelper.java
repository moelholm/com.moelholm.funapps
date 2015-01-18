package hello;

import org.springframework.stereotype.Component;

@Component
public class StringResultHelper {

    public String successResponse() {
        return "ok";
    }

    public String successResponse(String message) {
        if (message == null || message.trim().length() == 0) {
            return successResponse();
        }
        return String.format("%s: %s", successResponse(), message);
    }

    public String toErrorResponse(String message) {
        return String.format("error: %s", message);
    }

    public String toErrorResponse(Exception e) {
        return toErrorResponse(e.toString());
    }
}
