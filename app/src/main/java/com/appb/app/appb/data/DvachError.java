package com.appb.app.appb.data;

/**
 * Created by seishu on 27.02.2018.
 */

public class DvachError {
    int code;
    String error;

    public DvachError(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
