package com.manager.app.retrofit;

import com.manager.app.model.DanhSachLuatModel;
import com.manager.app.model.LoaiSPModel;
import com.manager.app.model.MessageModel;
import com.manager.app.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getLuaChon.php")
    Observable<LoaiSPModel> getLoaiSP();

    @GET("getLuat.php")
    Observable<DanhSachLuatModel> getDanhSachLuat();

    @POST("getDieu.php")
    @FormUrlEncoded
    Observable<DanhSachLuatModel> getDieuLuat(
            @Field("page") int page,
            @Field("dieu") int dieu
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<DanhSachLuatModel> search(
      @Field("search") String search
    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaLuat(
            @Field("id") int id
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("themluat.php")
    @FormUrlEncoded
    Observable<MessageModel> themLuat(
            @Field("chuong") String chuong,
            @Field("noidungchuong") String noidungchuong,
            @Field("muc") String muc,
            @Field("noidungmuc") String noidungmuc,
            @Field("dieu") String dieu,
            @Field("noidungdieu") String noidungdieu,
            @Field("khoan") String khoan,
            @Field("noidungkhoan") String noidungkhoan,
            @Field("mucphatduoi") String mucphatduoi,
            @Field("mucphattren") String mucphattren
    );

    @POST("sua.php")
    @FormUrlEncoded
    Observable<MessageModel> suaLuat(
            @Field("id") int id,
            @Field("chuong") String chuong,
            @Field("noidungchuong") String noidungchuong,
            @Field("muc") String muc,
            @Field("noidungmuc") String noidungmuc,
            @Field("dieu") String dieu,
            @Field("noidungdieu") String noidungdieu,
            @Field("khoan") String khoan,
            @Field("noidungkhoan") String noidungkhoan,
            @Field("mucphatduoi") String mucphatduoi,
            @Field("mucphattren") String mucphattren
    );

}
