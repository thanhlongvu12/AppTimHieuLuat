package com.manager.app.model;

public class LoaiSP {
    int id;
    String tensanpham;
    String hinhanh;

    public String getTenluachon() {
        return tenluachon;
    }

    public void setTenluachon(String tenluachon) {
        this.tenluachon = tenluachon;
    }

    String tenluachon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
