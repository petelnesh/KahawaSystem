package com.kahawa.development.kahawasystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kahawa.development.kahawasystem.Models.Databases.DatabaseHandler;
import com.kahawa.development.kahawasystem.UI.Activities.CustomVolleyRequest;
import com.kahawa.development.kahawasystem.Models.Beans.NewsModel;
import com.kahawa.development.kahawasystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mwatha on 01-Aug-16.
 */
public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder> {

    private Context context;
    String name;
    private ImageLoader imageloader;
    List<NewsModel> newsModelList;

    public ListNewsAdapter(Context context, List<NewsModel> newsModelList) {
        this.context = context;
        this.newsModelList = newsModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListNewsAdapter.ViewHolder holder, int position) {
        NewsModel newsModel=newsModelList.get(position);
        imageloader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageloader.get(newsModel.getImage(), ImageLoader.getImageListener(holder.news_icon, R.drawable.ic_holder, android.R.drawable.ic_dialog_alert));

        holder.news_icon.setImageUrl(newsModel.getImage(), imageloader);
        holder.title.setText(newsModel.getTitle());
        holder.date.setText(newsModel.getTime());
        holder.content.setText(newsModel.getContent());
        name=newsModel.getTitle();
    }

    @Override
    public int getItemCount() {
        return newsModelList.size() ;
    }

      class ViewHolder extends RecyclerView.ViewHolder {
          public TextView title,date, content;
          public NetworkImageView news_icon;
          public CheckBox favorite_button;
          public ViewHolder(View itemView) {
              super(itemView);
              title=(TextView)itemView.findViewById(R.id.title);
              date=(TextView)itemView.findViewById(R.id.date);
              content=(TextView)itemView.findViewById(R.id.content);
              news_icon=(NetworkImageView)itemView.findViewById(R.id.news_icon);
              favorite_button=(CheckBox) itemView.findViewById(R.id.favorite_button);
              favorite_button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      DatabaseHandler databaseHandler= new DatabaseHandler(context);
                      if(favorite_button.isChecked()){
                          String timestamp=new SimpleDateFormat("HH:mm dd/MM").format(new Date());
                          databaseHandler.insertFavorite(name,timestamp);
                          Toast.makeText(context,"Adding to favorite",Toast.LENGTH_SHORT).show();
                      }
                      if (!favorite_button.isChecked())
                      {
                          Toast.makeText(context,"Removing from favorite",Toast.LENGTH_SHORT).show();
                      }
                  }
              });
          }
      }
}