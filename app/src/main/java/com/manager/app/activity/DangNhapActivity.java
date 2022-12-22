package com.manager.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manager.app.R;
import com.manager.app.retrofit.ApiBanHang;
import com.manager.app.retrofit.RetrofitClient;
import com.manager.app.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    EditText email, pass;
    AppCompatButton buttondangnhap;
    ApiBanHang apiDangNhap;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        initControll();
    }

    private void initControll() {
        buttondangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Cần nhập Email", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Cần nhập Pasword", Toast.LENGTH_SHORT).show();
                }else {
                    compositeDisposable.add(apiDangNhap.dangNhap(str_email, str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                            Utils.user_current = userModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
//                                            Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();

                                    },
                                    Throwable -> {
                                        Toast.makeText(getApplicationContext(), "Khong thanh cong", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void anhXa() {
        apiDangNhap = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.emaildangnhap);
        pass = findViewById(R.id.passdangnhap);
        buttondangnhap = findViewById(R.id.btndangnhap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}