package com.example.dellpc.khabri.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dellpc.khabri.GlideApp;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.activity.WebviewActivity;
import com.example.dellpc.khabri.model.NewsApiModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingNewsAdapter extends RecyclerView.Adapter<TrendingNewsAdapter.TrendiniigViewGolder> {

    private Context context;
    private List<NewsApiModel> apiModelList;

    public TrendingNewsAdapter(Context ctx, List<NewsApiModel> modelList){
        this.context=ctx;
        this.apiModelList=modelList;
    }

    @NonNull
    @Override
    public TrendiniigViewGolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_trending,viewGroup,false);
        return new TrendiniigViewGolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendiniigViewGolder trendiniigViewGolder, int i) {
        trendiniigViewGolder.PerformAction(apiModelList.get(i));
    }

    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    class TrendiniigViewGolder extends RecyclerView.ViewHolder {

        @BindView(R.id.url_to_image)
        ImageView url_to_image;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.news_description)
        TextView news_description;

        @BindView(R.id.author_name)
        TextView author_name;

        @BindView(R.id.publishedAt)
        TextView publishedAt;

        @BindView(R.id.news_content)
        TextView content;

        @BindView(R.id.website_name)
        TextView website_name;

        @BindView(R.id.cardghhh)
        CardView cardghhh;

        public TrendiniigViewGolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

       void PerformAction(final NewsApiModel model){

           GlideApp.with(context.getApplicationContext()).load(model.getUrlToImage()).error(R.mipmap.ic_launcher).into(url_to_image);
           title.setText(model.getTitle());
           news_description.setText(model.getDescription());
           author_name.setText(model.getAuthor());
           publishedAt.setText(model.getPublishedAt());
           content.setText(model.getContent());
           website_name.setText(model.getName());

           cardghhh.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   context.startActivity(new Intent(context,WebviewActivity.class).putExtra("urltoload",model.getUrl()).putExtra("title",model.getTitle()));
               }
           });

        }
    }
}
