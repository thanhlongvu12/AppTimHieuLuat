package com.manager.app.model.EventBus;

import com.manager.app.model.DanhSachLuat;

public class SuaXoaEvent {
    DanhSachLuat danhSachLuat;

    public SuaXoaEvent(DanhSachLuat danhSachLuat) {
        this.danhSachLuat = danhSachLuat;
    }

    public DanhSachLuat getDanhSachLuat() {
        return danhSachLuat;
    }

    public void setDanhSachLuat(DanhSachLuat danhSachLuat) {
        this.danhSachLuat = danhSachLuat;
    }
}
