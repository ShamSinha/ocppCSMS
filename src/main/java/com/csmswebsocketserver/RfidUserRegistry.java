package com.csmswebsocketserver;

import datatype.dateTime;
import enumdatatype.AuthorizationStatusEnumType;
import enumdatatype.IdTokenEnumType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public final class RfidUserRegistry {
    private static final Map<String, RfidUser> USERS = new ConcurrentHashMap<String, RfidUser>();

    static {
        upsert("1234", IdTokenEnumType.KeyCode, "Demo PIN User", AuthorizationStatusEnumType.Accepted);
        upsert("04AABBCC", IdTokenEnumType.ISO14443, "Demo RFID User", AuthorizationStatusEnumType.Accepted);
    }

    private RfidUserRegistry() {
    }

    public static RfidUser upsert(String idToken, IdTokenEnumType tokenType, String userName,
                                  AuthorizationStatusEnumType status) {
        String normalizedToken = normalize(idToken);
        String safeUserName = userName == null || userName.trim().length() == 0
                ? "EV Driver"
                : userName.trim();
        RfidUser user = new RfidUser(
                normalizedToken,
                tokenType == null ? IdTokenEnumType.ISO14443 : tokenType,
                safeUserName,
                status == null ? AuthorizationStatusEnumType.Accepted : status,
                new dateTime().dT());
        USERS.put(normalizedToken, user);
        return user;
    }

    public static RfidUser find(String idToken) {
        return USERS.get(normalize(idToken));
    }

    public static boolean delete(String idToken) {
        return USERS.remove(normalize(idToken)) != null;
    }

    public static JsonObject usersPayload() {
        JsonArrayBuilder users = Json.createArrayBuilder();
        for (RfidUser user : sortedUsers()) {
            users.add(user.toJson());
        }
        return Json.createObjectBuilder()
                .add("type", "RfidUsers")
                .add("users", users)
                .build();
    }

    public static String normalize(String idToken) {
        return idToken == null ? "" : idToken.trim().toUpperCase(Locale.US);
    }

    private static List<RfidUser> sortedUsers() {
        List<RfidUser> users = new ArrayList<RfidUser>(USERS.values());
        Collections.sort(users, new Comparator<RfidUser>() {
            @Override
            public int compare(RfidUser left, RfidUser right) {
                return left.getIdToken().compareTo(right.getIdToken());
            }
        });
        return users;
    }
}
