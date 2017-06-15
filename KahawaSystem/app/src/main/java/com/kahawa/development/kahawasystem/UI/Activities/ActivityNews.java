package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.kahawa.development.kahawasystem.Adapters.ListNewsAdapter;
import com.kahawa.development.kahawasystem.Configs.Configurations;
import com.kahawa.development.kahawasystem.Models.Beans.NewsModel;
import com.kahawa.development.kahawasystem.R;
import com.kahawa.development.kahawasystem.RecyclerViewItems.DividerItemDecoration;
import com.kahawa.development.kahawasystem.RecyclerViewItems.RecyclerItemClickListener;
import com.kahawa.development.kahawasystem.UI.Fragments.DetailedNewsfragmentt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityNews extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    NewsModel newsModel;
    private RequestQueue requestQueue;
    List<NewsModel> newsmodelList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestCount = 1;
    Context context;
    String date,title,content,location;
    DetailedNewsfragmentt detailedNewsfragmentt;
    public ActivityNews() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainkahawa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.hotel_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView=(RecyclerView) findViewById(R.id.hotel_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
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
        //our Drawerlayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigationview responsible for showing the side drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getData();
        newsmodelList= new ArrayList<>();
        adapter = new ListNewsAdapter(this, newsmodelList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        TextView tvTitle=(TextView)view.findViewById(R.id.title);
                        title=tvTitle.getText().toString();

                        TextView tvDate=(TextView)view.findViewById(R.id.date);
                        date=tvDate.getText().toString();

                        TextView tvContent=(TextView)view.findViewById(R.id.content);
                        content=tvContent.getText().toString();

                        NetworkImageView image=(NetworkImageView) view.findViewById(R.id.news_icon);
                        String url=image.getImageURL();
                        //passing the values to the fragment

                        Bundle args= new Bundle();
                        args.putString("title",title);
                        args.putString("date",date);
                        args.putString("content",content);
                        args.putString("url",url);
                        FragmentManager fm=getSupportFragmentManager();
                        detailedNewsfragmentt= new DetailedNewsfragmentt();
                        // The device is smaller, so show the fragment fullscreen
                        FragmentTransaction transaction = fm.beginTransaction();
                        // For a little polish, specify a transition animation
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        //setting the arguments to the fragment
                        detailedNewsfragmentt.setArguments(args);
                        // To make it fullscreen, use the 'content' root view as the container
                        // for the fragment, which is always the root view for the activity
                        transaction.add(android.R.id.content, detailedNewsfragmentt)
                                .addToBackStack(null).commit();
                    }
                })
        );

    }
    public void dismissDialog(View view)
    {
        detailedNewsfragmentt.dismiss();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        SharedPreferences sharedPrefernces=this.getSharedPreferences(Configurations.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        boolean createdAt=sharedPrefernces.getBoolean(Configurations.CREATEDACCOUNT_SHARED_PREF,false);
        int id=item.getItemId();
        if(id==R.id.home)
        {
        }
        if(id==R.id.buy)
        {
            startActivity(new Intent(this,BuyActivity.class));
        }
        if(id==R.id.favorite)
        {
            startActivity(new Intent(ActivityNews.this,FavActivity.class));
        }
        if(id==R.id.chat)
        {
            if(!createdAt)
            {
                startActivity(new Intent(ActivityNews.this,SignUp.class));
            }
            else if(createdAt){
                startActivity(new Intent(ActivityNews.this,AddTopic.class));
                }
        }
        if(id==R.id.user_account)
        {
            if(!createdAt)
            {
                startActivity(new Intent (ActivityNews.this,SignUp.class));
            }
            else if(createdAt)
            {
                startActivity(new Intent(ActivityNews.this,LoginActivity.class));
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getData() {

        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Configurations.NEWS_URL + String.valueOf(requestCount),
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
            newsModel = new NewsModel();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the foodItems object
                newsModel.setTitle(json.getString(Configurations.NEWS_TITLE));
                newsModel.setContent(json.getString(Configurations.NEWS_CONTENT));
                newsModel.setTime(json.getString(Configurations.NEWS_DATE));
                newsModel.setImage(json.getString(Configurations.NEWS_IMAGE_URL));
                newsModel.setLocation(json.getString(Configurations.NEWS_LOCATION));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodItems object to the list
            newsmodelList.add(newsModel);
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
