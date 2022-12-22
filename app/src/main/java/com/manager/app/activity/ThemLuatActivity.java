package com.manager.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.manager.app.R;
import com.manager.app.databinding.ActivityThemLuatBinding;
import com.manager.app.model.DanhSachLuat;
import com.manager.app.retrofit.ApiBanHang;
import com.manager.app.retrofit.RetrofitClient;
import com.manager.app.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphCardView;

public class ThemLuatActivity extends AppCompatActivity {
    ActivityThemLuatBinding binding;
    ApiBanHang apiThemLuat;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    DanhSachLuat danhSachLuatSua;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemLuatBinding.inflate(getLayoutInflater());
        apiThemLuat = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        danhSachLuatSua = (DanhSachLuat) intent.getSerializableExtra("sua");

        if (danhSachLuatSua == null){
            flag = false;
        }else {
            flag = true;
            binding.btnthem.setText("Sửa sản phẩm");

            binding.sochuongthem.setText(String.valueOf(danhSachLuatSua.getChuong()));
            binding.somuc.setText(String.valueOf(danhSachLuatSua.getMuc()));
            binding.sodieu.setText(String.valueOf(danhSachLuatSua.getDieu()));
            binding.sokhoan.setText(String.valueOf(danhSachLuatSua.getKhoan()));
            binding.noidungchuongt.setText(danhSachLuatSua.getNoidungchuong());
            binding.noidungmuct.setText(danhSachLuatSua.getNoidungmuc());
            binding.noidungdieut.setText(danhSachLuatSua.getNoidungdieu());
            binding.noidungkhoant.setText(danhSachLuatSua.getNoidungkhoan());
            binding.duoi.setText(danhSachLuatSua.getMucphatduoi());
            binding.phattren.setText(danhSachLuatSua.getMucphattren());
        }

        anhXa();
        initControll();
    }

    private void initControll() {
        binding.btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false){
                    themLuat();
                }else {
                    suaLuat();
                }

            }
        });

    }

    private void suaLuat() {
        String str_chuong = binding.sochuongthem.getText().toString().trim();
        String str_noidungchuong = binding.noidungchuongt.getText().toString().trim();
        String str_muc = binding.somuc.getText().toString().trim();
        String str_noidungmuc = binding.noidungmuct.getText().toString().trim();
        String str_dieu = binding.sodieu.getText().toString().trim();
        String str_noidungdieu = binding.noidungdieut.getText().toString().trim();
        String str_khoan = binding.sokhoan.getText().toString().trim();
        String str_noidungkhoan = binding.noidungkhoant.getText().toString().trim();
        String str_tienduoi = binding.duoi.getText().toString().trim();
        String str_tientren = binding.phattren.getText().toString().trim();

        if (TextUtils.isEmpty(str_chuong) || TextUtils.isEmpty(str_noidungchuong) || TextUtils.isEmpty(str_muc) || TextUtils.isEmpty(str_noidungmuc) || TextUtils.isEmpty(str_dieu) || TextUtils.isEmpty(str_noidungdieu) || TextUtils.isEmpty(str_khoan) || TextUtils.isEmpty(str_noidungkhoan)){
            Toast.makeText(getApplicationContext(), "vui long nhap du", Toast.LENGTH_SHORT).show();
        }else {
            compositeDisposable.add(apiThemLuat.suaLuat(danhSachLuatSua.getId(),str_chuong, str_noidungchuong, str_muc, str_noidungmuc, str_dieu, str_noidungdieu, str_khoan, str_noidungkhoan, str_tienduoi, str_tientren)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "sai", Toast.LENGTH_SHORT).show();
                                }
                            },
                            Throwable -> {
                                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    private void themLuat() {
        String str_chuong = binding.sochuongthem.getText().toString().trim();
        String str_noidungchuong = binding.noidungchuongt.getText().toString().trim();
        String str_muc = binding.somuc.getText().toString().trim();
        String str_noidungmuc = binding.noidungmuct.getText().toString().trim();
        String str_dieu = binding.sodieu.getText().toString().trim();
        String str_noidungdieu = binding.noidungdieut.getText().toString().trim();
        String str_khoan = binding.sokhoan.getText().toString().trim();
        String str_noidungkhoan = binding.noidungkhoant.getText().toString().trim();
        String str_tienduoi = binding.duoi.getText().toString().trim();
        String str_tientren = binding.phattren.getText().toString().trim();

        if (TextUtils.isEmpty(str_chuong) || TextUtils.isEmpty(str_noidungchuong) || TextUtils.isEmpty(str_muc) || TextUtils.isEmpty(str_noidungmuc) || TextUtils.isEmpty(str_dieu) || TextUtils.isEmpty(str_noidungdieu) || TextUtils.isEmpty(str_khoan) || TextUtils.isEmpty(str_noidungkhoan)){
            Toast.makeText(getApplicationContext(), "vui long nhap du", Toast.LENGTH_SHORT).show();
        }else {
            compositeDisposable.add(apiThemLuat.themLuat(str_chuong, str_noidungchuong, str_muc, str_noidungmuc, str_dieu, str_noidungdieu, str_khoan, str_noidungkhoan, str_tienduoi, str_tientren)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                       messageModel -> {
                            if (messageModel.isSuccess()){
                                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "sai", Toast.LENGTH_SHORT).show();
                            }
                       },
                       Throwable -> {
                           Toast.makeText(getApplicationContext(), "khong thanh cong", Toast.LENGTH_SHORT).show();
                       }
                    ));
        }
    }

    private void anhXa() {

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}