package com.manager.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.manager.app.R;
import com.manager.app.adapter.DanhSachDieu16Adapter;
import com.manager.app.model.DanhSachLuat;
import com.manager.app.retrofit.ApiBanHang;
import com.manager.app.retrofit.RetrofitClient;
import com.manager.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DieuLuatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int dieu;
    DanhSachDieu16Adapter danhSachLuat16Adapter;
    List<DanhSachLuat> listDieu16;

    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieu_luat);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        dieu = getIntent().getIntExtra("dieu", 16);
        
        anhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == listDieu16.size() - 1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listDieu16.add(null);
                danhSachLuat16Adapter.notifyItemInserted(listDieu16.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listDieu16.remove(listDieu16.size()-1);
                danhSachLuat16Adapter.notifyItemRemoved(listDieu16.size());
                page += 1;
                getData(page);
                danhSachLuat16Adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getDieuLuat(page, dieu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhSachLuatModel -> {
                            if (danhSachLuatModel.isSuccess()){
                                if (danhSachLuat16Adapter == null){
                                    listDieu16 = danhSachLuatModel.getResult();
                                    danhSachLuat16Adapter = new DanhSachDieu16Adapter(getApplicationContext(), listDieu16);
                                    recyclerView.setAdapter(danhSachLuat16Adapter);
                                }else {
                                    int index = listDieu16.size() - 1;
                                    int number_of_add = danhSachLuatModel.getResult().size();
                                    for (int i = 0; i < number_of_add; i++){
                                        listDieu16.add(danhSachLuatModel.getResult().get(i));
                                    }
                                    danhSachLuat16Adapter.notifyItemRangeInserted(index, number_of_add);
                                }
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Disconnected to server", Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void anhXa() {
        toolbar = findViewById(R.id.toolbardieuluat);
        recyclerView = findViewById(R.id.recyclerview_dieu_luat);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        listDieu16 = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}