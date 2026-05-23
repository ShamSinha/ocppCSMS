package com.csmswebsocketserver;

import enumdatatype.AuthorizationStatusEnumType;
import enumdatatype.IdTokenEnumType;
import javax.json.Json;
import javax.json.JsonObject;

public class RfidUser {
    private final String idToken;
    private final IdTokenEnumType tokenType;
    private final String userName;
    private final AuthorizationStatusEnumType status;
    private final String updatedAt;

    public RfidUser(String idToken, IdTokenEnumType tokenType, String userName,
                    AuthorizationStatusEnumType status, String updatedAt) {
        this.idToken = idToken;
        this.tokenType = tokenType;
        this.userName = userName;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public String getIdToken() {
        return idToken;
    }

    public IdTokenEnumType getTokenType() {
        return tokenType;
    }

    public String getUserName() {
        return userName;
    }

    public AuthorizationStatusEnumType getStatus() {
        return status;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("idToken", idToken)
                .add("tokenType", tokenType.name())
                .add("userName", userName)
                .add("status", status.name())
                .add("updatedAt", updatedAt)
                .build();
    }
}
