package com.manager.app.model;

import java.io.Serializable;

public class DanhSachLuat implements Serializable {
    int id;
    int chuong;
    String noidungchuong;
    int muc;
    String noidungmuc;
    int dieu;
    String noidungdieu;
    int khoan;
    String noidungkhoan;
    String mucphatduoi;
    String mucphattren;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChuong() {
        return chuong;
    }

    public void setChuong(int chuong) {
        this.chuong = chuong;
    }

    public String getNoidungchuong() {
        return noidungchuong;
    }

    public void setNoidungchuong(String noidungchuong) {
        this.noidungchuong = noidungchuong;
    }

    public int getMuc() {
        return muc;
    }

    public void setMuc(int muc) {
        this.muc = muc;
    }

    public String getNoidungmuc() {
        return noidungmuc;
    }

    public void setNoidungmuc(String noidungmuc) {
        this.noidungmuc = noidungmuc;
    }

    public int getDieu() {
        return dieu;
    }

    public void setDieu(int dieu) {
        this.dieu = dieu;
    }

    public String getNoidungdieu() {
        return noidungdieu;
    }

    public void setNoidungdieu(String noidungdieu) {
        this.noidungdieu = noidungdieu;
    }

    public int getKhoan() {
        return khoan;
    }

    public void setKhoan(int khoan) {
        this.khoan = khoan;
    }

    public String getNoidungkhoan() {
        return noidungkhoan;
    }

    public void setNoidungkhoan(String noidungkhoan) {
        this.noidungkhoan = noidungkhoan;
    }

    public String getMucphatduoi() {
        return mucphatduoi;
    }

    public void setMucphatduoi(String mucphatduoi) {
        this.mucphatduoi = mucphatduoi;
    }

    public String getMucphattren() {
        return mucphattren;
    }

    public void setMucphattren(String mucphattren) {
        this.mucphattren = mucphattren;
    }
}
