package com.example.demo1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

// ① 创建Adapter
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH>{

    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//下拉刷新

    private Context mContext;

    private List<News> mDatas;

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public ImageView background;
        public ImageView profile;
        public TextView title;
        public TextView author;
        public TextView date;
        public VH(View v) {
            super(v);
            background=v.findViewById(R.id.image_background);
            profile=v.findViewById(R.id.image_profile);
            title = v.findViewById(R.id.tv_title);
            author = v.findViewById(R.id.tv_author);
            date=v.findViewById(R.id.tv_date);
        }
    }

    public NormalAdapter(List<News> data, Context context) {
        mContext = context;
        this.mDatas = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==mDatas.size()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private String dateParse(String oldDate){
        SimpleDateFormat OldDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat NewFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date=null;
        try {
            date=NewFormat.format(OldDateFormat.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //③ 在Adapter中实现3个方法
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.title.setText(mDatas.get(position).getText());
        holder.author.setText(mDatas.get(position).getName());
        holder.date.setText(dateParse(mDatas.get(position).getPasstime()));
        RequestOptions requstOption =new RequestOptions()
                //.placeholder(R.drawable.img_default)//图片加载出来前，显示的图片
                //.fallback( R.drawable.img_blank) //url为空的时候,显示的图片
                .error(R.mipmap.img_load_failure)//图片加载失败后，显示的图片
                .fitCenter();
        //加载失败
        requstOption.error(R.mipmap.ic_launcher);
        RequestOptions myOption =new RequestOptions()
                .fitCenter();
        Glide.with(mContext)
                .load(mDatas.get(position).getThumbnail())
                .apply(myOption)
                .into(holder.background);
        Glide.with(mContext)
                .load(mDatas.get(position).getHeader())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
        return new VH(v);
    }
}
