package com.manager.app.model;

import java.util.List;

public class DanhSachLuatModel {
    boolean success = true;
    String message;
    List<DanhSachLuat> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DanhSachLuat> getResult() {
        return result;
    }

    public void setResult(List<DanhSachLuat> result) {
        this.result = result;
    }
}
