package com.manager.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.manager.app.R;
import com.manager.app.model.DanhSachLuat;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView chuong, muc, dieu, khoan, mucphattren, mucphatduoi;
    TextView noidungchuong, noidungmuc, noidungdieu, noidungkhoan;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);

        anhXa();
        ActionToolBar();
        initData();
    }

    private void initData() {
        DanhSachLuat danhSachLuat = (DanhSachLuat) getIntent().getSerializableExtra("chitiet");
        chuong.setText(String.valueOf(danhSachLuat.getChuong()));
        muc.setText(String.valueOf(danhSachLuat.getMuc()));
        dieu.setText(String.valueOf(danhSachLuat.getDieu()));
        khoan.setText(String.valueOf(danhSachLuat.getKhoan()));
        noidungchuong.setText(danhSachLuat.getNoidungchuong());
        noidungmuc.setText(danhSachLuat.getNoidungmuc());
        noidungdieu.setText(danhSachLuat.getNoidungdieu());
        noidungkhoan.setText(danhSachLuat.getNoidungkhoan());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mucphatduoi.setText("" + decimalFormat.format(Double.parseDouble(danhSachLuat.getMucphatduoi())) + "Đ");
        mucphattren.setText("" + decimalFormat.format(Double.parseDouble(danhSachLuat.getMucphattren())) + "Đ");
    }


    private void anhXa() {
        chuong = findViewById(R.id.sochuongchitiet);
        muc = findViewById(R.id.somucchitiet);
        dieu = findViewById(R.id.sodieuchitiet);
        khoan =findViewById(R.id.sokhoanchitiet);
        noidungchuong = findViewById(R.id.noidungchuongchitiet);
        noidungmuc = findViewById(R.id.noidungmucchitiet);
        noidungdieu = findViewById(R.id.noidungdieuchitiet);
        noidungkhoan = findViewById(R.id.noidungkhoanchitiet);
        toolbar = findViewById(R.id.toobarchitiet);
        mucphatduoi = findViewById(R.id.mucphatduoichitiet);
        mucphattren = findViewById(R.id.mucphatrenchitiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}