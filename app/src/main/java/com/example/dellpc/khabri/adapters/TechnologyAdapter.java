package com.example.dellpc.khabri.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dellpc.khabri.GlideApp;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.activity.WebviewActivity;
import com.example.dellpc.khabri.model.NewsApiModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechnologyAdapter  extends RecyclerView.Adapter<TechnologyAdapter.TechnologyViewGolder> {

    private Context context;
    private List<NewsApiModel> apiModelList;

    public TechnologyAdapter(Context ctx, List<NewsApiModel> modelList){
        this.context=ctx;
        this.apiModelList=modelList;
    }


    @NonNull
    @Override
    public TechnologyViewGolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gadgets,viewGroup,false);
        return new TechnologyViewGolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnologyViewGolder technologyViewGolder, int i) {
        technologyViewGolder.PerformAction(apiModelList.get(i));
    }



    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    class TechnologyViewGolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_gadgets)
        LinearLayout ll_gadgets;
        @BindView(R.id.imageView_url)
        ImageView imageView_url;
        @BindView(R.id.title)
        TextView title;


        public TechnologyViewGolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void PerformAction(final NewsApiModel model){

            GlideApp.with(context.getApplicationContext()).load(model.getUrlToImage()).error(R.mipmap.ic_launcher).into(imageView_url);
            title.setText(model.getTitle());

            ll_gadgets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,WebviewActivity.class).putExtra("urltoload",model.getUrl()).putExtra("title",model.getTitle()));
                }
            });
        }
    }
}