package com.csmswebsocketserver;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class JsonObjectTextDecoder implements Decoder.Text<JsonObject> {
    @Override
    public JsonObject decode(String message) throws DecodeException {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            return reader.readObject();
        } catch (JsonException | IllegalStateException e) {
            throw new DecodeException(message, "Invalid JSON object", e);
        }
    }

    @Override
    public boolean willDecode(String message) {
        if (message == null || !message.trim().startsWith("{")) {
            return false;
        }
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            reader.readObject();
            return true;
        } catch (JsonException | IllegalStateException e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
