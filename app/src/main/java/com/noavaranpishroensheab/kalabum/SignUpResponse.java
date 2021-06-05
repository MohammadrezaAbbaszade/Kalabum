package com.noavaranpishroensheab.kalabum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class SignUpResponse {

    @Expose
    @SerializedName("debug")
    private List<String> debug;
    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("locale")
    private String locale;
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("success")
    private boolean success;

    public List<String> getDebug() {
        return debug;
    }

    public void setDebug(List<String> debug) {
        this.debug = debug;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Data {
        @Expose
        @SerializedName("password")
        private List<String> password;
        @Expose
        @SerializedName("last_name")
        private List<String> last_name;
        @Expose
        @SerializedName("first_name")
        private List<String> first_name;
        @Expose
        @SerializedName("phone_number")
        private List<String> phone_number;

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }

        public List<String> getLast_name() {
            return last_name;
        }

        public void setLast_name(List<String> last_name) {
            this.last_name = last_name;
        }

        public List<String> getFirst_name() {
            return first_name;
        }

        public void setFirst_name(List<String> first_name) {
            this.first_name = first_name;
        }

        public List<String> getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(List<String> phone_number) {
            this.phone_number = phone_number;
        }
    }
}
