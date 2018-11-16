package com.example.dellpc.khabri.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.adapters.Home_newsAdapter;
import com.example.dellpc.khabri.model.NewsApiModel;
import com.example.dellpc.khabri.network.HttpUrl;
import com.example.dellpc.khabri.data.SharedPrefsHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    @BindView(R.id.trending_rcv)
    RecyclerView trending_rcv;

    @BindView(R.id.local_rcv)
    RecyclerView local_rcv;
    
    @BindView(R.id.latest_rcv)
    RecyclerView latest_rcv;

    @BindView(R.id.viewAll_trending)
    Button viewAll_trending;
    @BindView(R.id.viewAll_area)
    Button viewAll_area;
    @BindView(R.id.viewAll_latest)
    Button viewAll_latest;

    private Context mContext;
    RequestQueue queue;
    private ShimmerFrameLayout mShimmerViewContainer;

    List<NewsApiModel> modelListTrending= new ArrayList<>();
    List<NewsApiModel> modelListArea= new ArrayList<>();
    List<NewsApiModel> modelListLatest= new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private SharedPrefsHelper mPref;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mPref= new SharedPrefsHelper(mContext);
        try {
            gethomeTopics();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLocalTopics();
        getLatestTopics();
        mShimmerViewContainer.startShimmerAnimation();
        return view;
    }

    @OnClick({R.id.viewAll_trending})
    void openNewTrending(){
        onButtonPressed(modelListTrending);
    }

    @OnClick({R.id.viewAll_area})
    void openNewArea(){
        onButtonPressed(modelListArea);
    }

    @OnClick(R.id.viewAll_latest)
    void openNewLatest(){

        onButtonPressed(modelListLatest);
    }


    private void getLatestTopics()  {

        {
            queue = Volley.newRequestQueue(mContext);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date c = Calendar.getInstance().getTime();


            String sDate = df.format(c);
            String url =HttpUrl.getUrl(mContext,"trending",sDate,sDate).toString();

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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

                                    modelListLatest.add(model);
                                }

                                latest_rcv.setLayoutManager(new GridLayoutManager(mContext, 3));
                                latest_rcv.setAdapter(new Home_newsAdapter(mContext,modelListLatest));

                                // Stopping Shimmer Effect's animation after data is loaded to ListView
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);

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
    }

    private void getLocalTopics() {

        {
            queue = Volley.newRequestQueue(mContext);


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date c = Calendar.getInstance().getTime();


            String sDate = df.format(c);
            String url =HttpUrl.getUrl(mContext,"latest",sDate,sDate).toString();

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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

                                    modelListArea.add(model);
                                }

                                local_rcv.setAdapter(new Home_newsAdapter(mContext,modelListArea));

                                // Stopping Shimmer Effect's animation after data is loaded to ListView
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);

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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void gethomeTopics() throws IOException {
        queue = Volley.newRequestQueue(mContext);

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;

        addresses = geocoder.getFromLocation(Double.parseDouble(mPref.getLatitude()), Double.parseDouble(mPref.getLongitude()), 1);

        //String cityName = addresses.get(0).getAddressLine(0);
        String stateName;
        if (addresses.size() > 0){
             stateName = addresses.get(0).getAdminArea();
            Log.e("cityName",stateName+"nul");
        } else {
             stateName="india";
        }



        if (addresses.size() > 0)
            System.out.println(addresses.get(0).getLocality());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();


        String sDate = df.format(c);
        String url =HttpUrl.getUrl(mContext,stateName,sDate,sDate).toString();


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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

                                modelListTrending.add(model);
                            }

                            trending_rcv.setAdapter(new Home_newsAdapter(mContext,modelListTrending));

                            // Stopping Shimmer Effect's animation after data is loaded to ListView
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);

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

    public void onButtonPressed(List<NewsApiModel> modelLis) {
        if (mListener != null) {
            mListener.onFragmentInteraction(modelLis);
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(List<NewsApiModel> modelLis);
    }

}
