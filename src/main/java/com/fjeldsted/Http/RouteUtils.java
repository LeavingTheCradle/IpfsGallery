package com.fjeldsted.Http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import com.fjeldsted.Http.Json.*;
import com.fjeldsted.Http.Json.Error;
import org.json.JSONObject;

public class RouteUtils {

    public void _sendImage(HttpExchange ex, byte[] image, String mimetype) {
        try {
            ex.getResponseHeaders().set("Content-Type", mimetype);
            ex.sendResponseHeaders(200, image.length);// response code and length
            OutputStream os = ex.getResponseBody();
            os.write(image);
            os.close();
        } catch (IOException e) {

        }
    }

    public void _sendJson(HttpExchange ex, int code, JSONObject data) {
        try {
            ex.sendResponseHeaders(200, data.toString().getBytes().length);// response code and length
            OutputStream os = ex.getResponseBody();
            os.write(data.toString().getBytes());
            os.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public JSONObject _getError(String code, String message, String id) {
        Error e = new Error();
        e.setCode(code);
        e.setErrorId(id);
        e.setMessage(message);
        return new JSONObject(e);
    }
}
