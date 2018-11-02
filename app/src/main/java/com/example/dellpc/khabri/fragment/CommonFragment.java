package com.example.dellpc.khabri.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.adapters.Home_newsAdapter;
import com.example.dellpc.khabri.adapters.TechnologyAdapter;
import com.example.dellpc.khabri.model.NewsApiModel;
import com.example.dellpc.khabri.network.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonFragment extends Fragment {

    private Context mContext;
    RequestQueue queue;
    private ArrayList< NewsApiModel> apiModelList= new ArrayList<>();

    @BindView(R.id.trending_rcv)
    RecyclerView trending_rcv;
    public CommonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trending, container, false);
        ButterKnife.bind(this,view);

        Bundle args = getArguments();
        if (args!=null){
            if (args.containsKey("topic")){
                String topic = args.getString("topic");
                try {
                    gethomeTopics(topic);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else if (args.containsKey("NewsApiModel")){
               // apiModelList = args.getSerializable("NewsApiModel");
                ArrayList<NewsApiModel> listwa= (ArrayList<NewsApiModel>) args.getSerializable("NewsApiModel");

                trending_rcv.setAdapter(new TechnologyAdapter(mContext,listwa));
                Log.e("apiModelList",listwa.size()+"null");
            }
        }


        ;

        return view;
    }


    private void gethomeTopics(String myString) throws ParseException {
        queue = Volley.newRequestQueue(mContext);


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date c = Calendar.getInstance().getTime();


        String sDate = df.format(c);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;

            date = dateFormat.parse(sDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String yesterdayAsString = dateFormat.format(calendar.getTime());



        URL url1 = HttpUrl.getUrl(mContext,myString,yesterdayAsString,df.format(c));

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);

                            JSONArray articles= jsonObject.getJSONArray("articles");

                            for (int i=0;i<articles.length();i++){
                                NewsApiModel model= new NewsApiModel();
                                JSONObject sequen= articles.getJSONObject(i);

                                JSONObject source=sequen.getJSONObject("source");
                                model.setId(source.getString("id"));
                                model.setName(source.getString("name"));
                                model.setAuthor(sequen.getString( "author"));
                                model.setTitle(sequen.getString(  "title"));
                                model.setDescription(sequen.getString(   "description"));
                                model.setUrl(sequen.getString( "url"));
                                model.setUrlToImage(sequen.getString("urlToImage"));
                                model.setPublishedAt(sequen.getString("publishedAt"));
                                model.setContent(sequen.getString("content"));

                                apiModelList.add(model);
                            }

                            trending_rcv.setAdapter(new Home_newsAdapter(mContext,apiModelList));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
