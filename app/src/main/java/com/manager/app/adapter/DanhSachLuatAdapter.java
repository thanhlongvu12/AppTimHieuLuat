package com.manager.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.style.BulletSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.app.Interface.ItemClickListener;
import com.manager.app.R;
import com.manager.app.activity.ChiTietActivity;
import com.manager.app.model.DanhSachLuat;
import com.manager.app.model.EventBus.SuaXoaEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DanhSachLuatAdapter extends RecyclerView.Adapter<DanhSachLuatAdapter.MyViewHodelDanhSachLuat> {
    Context context;
    List<DanhSachLuat> array;

    public DanhSachLuatAdapter(Context context, List<DanhSachLuat> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHodelDanhSachLuat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachluat, parent, false);

        return new MyViewHodelDanhSachLuat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodelDanhSachLuat holder, int position) {
        DanhSachLuat danhSachLuat = array.get(position);
        holder.chuong.setText(String.valueOf(danhSachLuat.getChuong()));
        holder.muc.setText(String.valueOf(danhSachLuat.getMuc()));
        holder.dieu.setText(String.valueOf(danhSachLuat.getDieu()));
        holder.khoan.setText(String.valueOf(danhSachLuat.getKhoan()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", danhSachLuat);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(danhSachLuat));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHodelDanhSachLuat extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView chuong, dieu, muc, khoan;
        private ItemClickListener itemClickListener;

        public MyViewHodelDanhSachLuat(@NonNull View itemView) {
            super(itemView);

            chuong = itemView.findViewById(R.id.sochuong);
            dieu = itemView.findViewById(R.id.sodieu);
            muc = itemView.findViewById(R.id.somuc);
            khoan = itemView.findViewById(R.id.sokhoan);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 0, getAdapterPosition(), "Sửa");
            contextMenu.add(0, 1, getAdapterPosition(), "Xóa");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
