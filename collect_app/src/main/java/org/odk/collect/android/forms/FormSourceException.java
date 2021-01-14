package org.odk.collect.android.forms;

public class FormSourceException extends Exception {

    public enum Type {
        UNREACHABLE,
        AUTH_REQUIRED,
        FETCH_ERROR,
        SECURITY_ERROR,
        SERVER_ERROR,
        PARSE_ERROR
    }

    private final Type type;
    private final String serverUrl;
    private final Integer responseCode;

    public FormSourceException(Type type) {
        this.type = type;
        this.serverUrl = null;
        this.responseCode = null;
    }

    public FormSourceException(Type type, String serverUrl) {
        this.type = type;
        this.serverUrl = serverUrl;
        this.responseCode = null;
    }
    public FormSourceException(Type type, Integer responseCode, String serverUrl) {
        this.type = type;
        this.serverUrl = serverUrl;
        this.responseCode = responseCode;
    }

    public Type getType() {
        return type;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public Integer getStatusCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return "FormAPIException{" +
                "type=" + type +
                '}';
    }
}
