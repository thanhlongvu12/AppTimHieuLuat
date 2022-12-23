package com.manager.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.manager.app.R;
import com.manager.app.adapter.DanhSachLuatAdapter;
import com.manager.app.adapter.LoaiSPAdapter;
import com.manager.app.model.DanhSachLuat;
import com.manager.app.model.LoaiSP;
import com.manager.app.retrofit.ApiBanHang;
import com.manager.app.retrofit.RetrofitClient;
import com.manager.app.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSPAdapter loaiSPAdapter;
    List<LoaiSP> mangLoaiSP;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    List<DanhSachLuat> listDanhSachLuat;
    DanhSachLuatAdapter luatAdapter;

    ImageView searchmanhinhchinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        anhXa();
        actionBar();
        if(isConnected(this)){
            actionViewFlipper();
            getLoaiSanPham();
            //getSPMoi();
            getLuat();
            getEventClickMenu();
        }else {
            Toast.makeText(getApplicationContext(), "khong co mang", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventClickMenu() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent dieu16 = new Intent(getApplicationContext(), DieuLuatActivity.class);
                        dieu16.putExtra("dieu", 16);
                        startActivity(dieu16);
                        break;
                    case 2:
                        Intent dieu17 = new Intent(getApplicationContext(), DieuLuatActivity.class);
                        dieu17.putExtra("dieu", 17);
                        startActivity(dieu17);
                        break;
                    case 3:
                        Intent quanly = new Intent(getApplicationContext(), QuanLyActivity.class);
                        startActivity(quanly);
                        break;
                }
            }
        });
    }


    private void getLuat() {
        compositeDisposable.add(apiBanHang.getDanhSachLuat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhSachLuatModel -> {
                            if (danhSachLuatModel.isSuccess())
                                listDanhSachLuat = danhSachLuatModel.getResult();
                                luatAdapter = new DanhSachLuatAdapter(getApplicationContext(), listDanhSachLuat);
                                recyclerViewManHinhChinh.setAdapter(luatAdapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Disconnceted to sever" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSP()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                loaiSPModel -> {
                                    if(loaiSPModel.isSuccess()){
                                        mangLoaiSP = loaiSPModel.getResult();
                                        //Khoi tao ADAPTER
                                        loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(), mangLoaiSP);
                                        listViewManHinhChinh.setAdapter(loaiSPAdapter);
                                    }
                                }
                        ));
    }

    private void actionViewFlipper() {
        List<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.anhluat1);
        listImage.add(R.drawable.anhluat2);
        listImage.add(R.drawable.anhluat3);
        for (int i=0; i < listImage.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(listImage.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);

        recyclerViewManHinhChinh = findViewById(R.id.recyclerViewManHinhChinh);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);

        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangLoaiSP = new ArrayList<>();

        listDanhSachLuat = new ArrayList<>();

        searchmanhinhchinh = findViewById(R.id.searchmanhinhchinh);

        searchmanhinhchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}