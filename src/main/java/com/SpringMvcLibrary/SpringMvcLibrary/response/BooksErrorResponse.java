package com.SpringMvcLibrary.SpringMvcLibrary.response;

public class BooksErrorResponse {
    private String massage;
    private  long timestamp;

    public BooksErrorResponse(String massage, long timestamp) {
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
