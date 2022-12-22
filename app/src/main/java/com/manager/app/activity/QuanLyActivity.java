package com.manager.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.app.R;
import com.manager.app.adapter.DanhSachLuatAdapter;
import com.manager.app.model.DanhSachLuat;
import com.manager.app.model.EventBus.SuaXoaEvent;
import com.manager.app.retrofit.ApiBanHang;
import com.manager.app.retrofit.RetrofitClient;
import com.manager.app.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphCardView;

public class QuanLyActivity extends AppCompatActivity {
    
    ImageView themluat;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiSuaXoa;
    List<DanhSachLuat> list;
    DanhSachLuatAdapter danhSachLuatAdapter;
    DanhSachLuat danhSachLuatXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        apiSuaXoa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        anhXa();
        initControll();
        getLuat();
    }

    private void initControll() {
        themluat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemLuatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getLuat() {
        compositeDisposable.add(apiSuaXoa.getDanhSachLuat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhSachLuatModel -> {
                            if (danhSachLuatModel.isSuccess())
                                list = danhSachLuatModel.getResult();
                                danhSachLuatAdapter = new DanhSachLuatAdapter(getApplicationContext(), list);
                                recyclerView.setAdapter(danhSachLuatAdapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Disconnceted to sever" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void anhXa() {
        themluat = findViewById(R.id.img_add);
        recyclerView = findViewById(R.id.recyclerview_ql);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            suaLuat();
        }else if(item.getTitle().equals("Xóa")){
            xoaLuat();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaLuat() {
        compositeDisposable.add(apiSuaXoa.xoaLuat(danhSachLuatXoa.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    messageModel -> {
                        if (messageModel.isSuccess()){
                            Toast.makeText(getApplicationContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                            getLuat();
                        }else {
                            Toast.makeText(getApplicationContext(), "Khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Thanh Cong", Toast.LENGTH_LONG).show();
                            getLuat();
                        }
                ));
    }

    private void suaLuat() {
        Intent intent = new Intent(getApplicationContext(), ThemLuatActivity.class);
        intent.putExtra("sua", danhSachLuatXoa);
        startActivity(intent);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
            if (event != null){
                danhSachLuatXoa = event.getDanhSachLuat();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}