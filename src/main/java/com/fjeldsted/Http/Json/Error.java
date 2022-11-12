package com.fjeldsted.Http.Json;

public class Error {
    private String Code;
    private String Message;
    private String ErrorId;// For matching to logs.

    public String getCode() {
        return this.Code;
    }

    public String getMessage() {
        return this.Message;
    }

    public String getErrorId() {
        return this.ErrorId;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setErrorId(String errorId) {
        this.ErrorId = errorId;
    }
}
