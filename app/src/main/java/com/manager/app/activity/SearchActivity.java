package com.manager.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText editSreach;
    DanhSachDieu16Adapter danhSachLuatAdapter;
    ApiBanHang apiSearch;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        anhXa();
        ActionToolBar();
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
        apiSearch = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        editSreach = findViewById(R.id.editsearch);
        toolbar = findViewById(R.id.toolbartimkiem);
        recyclerView = findViewById(R.id.recyclerview_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        danhSachLuatAdapter = new DanhSachDieu16Adapter(getApplicationContext());
        recyclerView.setAdapter(danhSachLuatAdapter);

        editSreach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                danhSachLuatAdapter.reset(new ArrayList<>());
                if (charSequence.length() > 0){
                    getDataSearch(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getDataSearch(String textSearch) {
        compositeDisposable.add(apiSearch.search(textSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        danhSachLuatModel -> {
                            if (danhSachLuatModel.isSuccess()){
                                danhSachLuatAdapter.reset(danhSachLuatModel.getResult());
                            }
                        },
                        Throwable ->{
                            Toast.makeText(getApplicationContext(), "Khong thanh cong", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}