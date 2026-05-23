/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

/**
 *
 * @author Shubham
 */
import enumdatatype.RPCErrorCodes;
import java.io.StringReader;
import java.nio.ByteBuffer;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoderboth implements Decoder.Text<WebsocketMessage>, Decoder.Binary<JsonObject> {

    private static final int CALL_MESSAGE_TYPE_ID = 2;
    private static final int CALLRESULT_MESSAGE_TYPE_ID = 3;
    private static final int CALLERROR_MESSAGE_TYPE_ID = 4;

    @Override
    public WebsocketMessage decode(String s) throws DecodeException {
        try {
            JsonArray message = jsonArrayFromString(s);
            int messagetypeId = message.getInt(0);
            String messageId = message.getString(1);

            if (messagetypeId == CALL_MESSAGE_TYPE_ID) {
                String action = message.getString(2);
                JsonObject payload = jsonObjectAt(message, 3);
                CALL.MessageTypeId = messagetypeId;
                CALL.MessageId = messageId;
                return new CALL(messageId, action, payload);
            }

            if (messagetypeId == CALLRESULT_MESSAGE_TYPE_ID) {
                JsonObject payload = jsonObjectAt(message, 2);
                CALLRESULT.MessageTypeId = messagetypeId;
                CALLRESULT.MessageId = messageId;
                return new CALLRESULT(messageId, payload);
            }

            if (messagetypeId == CALLERROR_MESSAGE_TYPE_ID) {
                RPCErrorCodes errorcode = RPCErrorCodes.valueOf(message.getString(2));
                String errordescription = message.getString(3);
                JsonObject errordetails = jsonObjectAt(message, 4);
                CALLERROR.MessageTypeId = messagetypeId;
                CALLERROR.MessageId = messageId;
                return new CALLERROR(messageId, errorcode, errordescription, errordetails);
            }

            throw new DecodeException(s, "Unsupported OCPP-J message type: " + messagetypeId);
        } catch (JsonException | IllegalArgumentException | IndexOutOfBoundsException | ClassCastException e) {
            throw new DecodeException(s, "Invalid OCPP-J message", e);
        }
    }

    private static JsonArray jsonArrayFromString(String jsonArrayStr) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonArrayStr))) {
            return jsonReader.readArray();
        }
    }

    private static JsonObject jsonObjectAt(JsonArray message, int index) {
        JsonValue value = message.get(index);
        if (value.getValueType() != JsonValue.ValueType.OBJECT) {
            throw new IllegalArgumentException("Expected payload object at index " + index);
        }
        return message.getJsonObject(index);
    }

    private static boolean hasStringAt(JsonArray message, int index) {
        return index < message.size()
                && message.get(index).getValueType() == JsonValue.ValueType.STRING;
    }

    private static boolean hasObjectAt(JsonArray message, int index) {
        return index < message.size()
                && message.get(index).getValueType() == JsonValue.ValueType.OBJECT;
    }

    @Override
    public boolean willDecode(String s) {
        try {
            JsonArray message = jsonArrayFromString(s);
            if (message.size() < 3) {
                return false;
            }

            int messagetypeId = message.getInt(0);
            if (messagetypeId == CALL_MESSAGE_TYPE_ID) {
                return message.size() == 4
                        && hasStringAt(message, 1)
                        && hasStringAt(message, 2)
                        && hasObjectAt(message, 3);
            }
            if (messagetypeId == CALLRESULT_MESSAGE_TYPE_ID) {
                return message.size() == 3
                        && hasStringAt(message, 1)
                        && hasObjectAt(message, 2);
            }
            if (messagetypeId == CALLERROR_MESSAGE_TYPE_ID) {
                return message.size() == 5
                        && hasStringAt(message, 1)
                        && hasStringAt(message, 2)
                        && hasStringAt(message, 3)
                        && hasObjectAt(message, 4);
            }
        } catch (JsonException | IllegalArgumentException | IndexOutOfBoundsException | ClassCastException e) {
            return false;
        }
        return false;
    }

    @Override
    public void init(EndpointConfig ec) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public JsonObject decode(ByteBuffer bb) throws DecodeException {
        return null;
    }

    @Override
    public boolean willDecode(ByteBuffer bb) {
        return false;
    }
}
