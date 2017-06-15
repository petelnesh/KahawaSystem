package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.kahawa.development.kahawasystem.Adapters.BuyAdapter;
import com.kahawa.development.kahawasystem.Configs.Configurations;
import com.kahawa.development.kahawasystem.Models.Beans.BuyModel;
import com.kahawa.development.kahawasystem.Models.Beans.NewsModel;
import com.kahawa.development.kahawasystem.R;
import com.kahawa.development.kahawasystem.RecyclerViewItems.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    BuyModel buyModel;
    private RequestQueue requestQueue;
    List<BuyModel> buyModelList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestCount = 1;

    public BuyActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Buy Commodities & Equipments");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.hotel_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView=(RecyclerView) findViewById(R.id.hotel_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLastItemDisplaying(recyclerView)) {
                    //Calling the method getdata again
                    getData();
                }
            }
        });


        getData();
        buyModelList= new ArrayList<>();
        adapter = new BuyAdapter(this, buyModelList);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
         new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override public void onItemClick(View view, int position) {
        TextView buy_name=(TextView) view.findViewById(R.id.buy_name);
        String name=buy_name.getText().toString();
        TextView buy_cost=(TextView) view.findViewById(R.id.buy_cost);
        String cost=buy_cost.getText().toString();
        NetworkImageView networkImageView=(NetworkImageView) view.findViewById(R.id.buy_icon);
        String imageurl=networkImageView.getImageURL();
        Intent intent=new Intent(getApplicationContext(),DetailedBuy.class);
        intent.putExtra("name",name);
        intent.putExtra("cost",cost);
        intent.putExtra("imageurl",imageurl);
        startActivity(intent);
        }
        })
         );
    }

    private void getData() {

        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Configurations.BUY_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "No more data available to load!", Toast.LENGTH_SHORT).show();


                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }


    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            buyModel = new BuyModel();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the foodItems object
                buyModel.setName(json.getString(Configurations.BUY_NAME));
                buyModel.setCost(json.getString(Configurations.BUY_COST));

                buyModel.setImage(json.getString(Configurations.BUY_IMAGE_URL));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodItems object to the list
            buyModelList.add(buyModel);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
