package com.strangeman.vipqa.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.Product;


import java.util.List;

/**
 * Created by pilot on 2017/5/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context mContext;
    private List<Product> mProductList;
    private OnItemClickListener onItemClickListener;


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
        }
    }

    public ProductAdapter(List<Product> productList){
        mProductList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v,(int)v.getTag());
                }
//                int position = holder.getAdapterPosition();
//                Product product = mProductList.get(position);
//                Intent intent = new Intent("android.intent.action.ACTION_START");
//                mContext.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        Glide.with(mContext).load(product.getImagePath()).into(holder.productImage);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}
