package com.example.dellpc.khabri.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dellpc.khabri.GlideApp;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.activity.WebviewActivity;
import com.example.dellpc.khabri.customUI.BloggerBoldTextView;
import com.example.dellpc.khabri.model.NewsApiModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home_newsAdapter extends RecyclerView.Adapter<Home_newsAdapter.Trending_newsAdapterViewHolder> {

    private Context context;
    private List<NewsApiModel> apiModelList;

    public Home_newsAdapter(Context ctx, List<NewsApiModel> modelList){
        this.context=ctx;
        this.apiModelList=modelList;
    }
    @NonNull
    @Override
    public Trending_newsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_topics,viewGroup,false);
        return new Trending_newsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Trending_newsAdapterViewHolder trending_newsAdapterViewHolder, int i) {
        trending_newsAdapterViewHolder.BindViews(apiModelList.get(i));
    }

    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    class Trending_newsAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.url_to_image_hot)
        ImageView url_to_image_hotL;

        @BindView(R.id.hot_heading)
        BloggerBoldTextView hot_heading;

        @BindView(R.id.card_container)
        CardView card_container;
        public Trending_newsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void BindViews(final NewsApiModel model){

            GlideApp.with(context.getApplicationContext()).load(model.getUrlToImage()).error(R.mipmap.ic_launcher).into(url_to_image_hotL);
            Log.e("dfsdfdsfsadf",model.getUrlToImage());
            hot_heading.setText(model.getTitle());

            card_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,WebviewActivity.class).putExtra("urltoload",model.getUrl()).putExtra("title",model.getTitle()));
                }
            });

        }
    }
}
