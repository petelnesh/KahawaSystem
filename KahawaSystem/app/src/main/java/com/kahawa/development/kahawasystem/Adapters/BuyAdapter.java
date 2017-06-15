package com.kahawa.development.kahawasystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kahawa.development.kahawasystem.Models.Beans.BuyModel;
import com.kahawa.development.kahawasystem.Models.Beans.NewsModel;
import com.kahawa.development.kahawasystem.R;
import com.kahawa.development.kahawasystem.UI.Activities.CustomVolleyRequest;


import java.util.List;

/**
 * Created by Mwatha on 01-Aug-16.
 */
public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.ViewHolder> {

    private Context context;
    private ImageLoader loadImage;
    List<BuyModel> buyModelList;

    public BuyAdapter(Context context, List<BuyModel> buyModelList1) {
        this.context = context;
        this.buyModelList = buyModelList1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BuyAdapter.ViewHolder holder, int position) {
        BuyModel buyModel=buyModelList.get(position);
        loadImage = CustomVolleyRequest.getInstance(context).getImageLoader();
        loadImage.get(buyModel.getImage(), ImageLoader.getImageListener(holder.buy_icon, R.drawable.ic_holder, android.R.drawable.ic_dialog_alert));


        holder.buy_icon.setImageUrl(buyModel.getImage(), loadImage);
        holder.buy_name.setText(buyModel.getName());
        holder.buy_cost.setText(buyModel.getCost());
    }

    @Override
    public int getItemCount() {
        return buyModelList.size() ;
    }

      class ViewHolder extends RecyclerView.ViewHolder {
          public TextView buy_name,buy_cost, content;
          public NetworkImageView buy_icon;
          public ViewHolder(View itemView) {
              super(itemView);
              buy_name=(TextView)itemView.findViewById(R.id.buy_name);
              buy_cost=(TextView)itemView.findViewById(R.id.buy_cost);
              buy_icon=(NetworkImageView)itemView.findViewById(R.id.buy_icon);
          }
      }
}