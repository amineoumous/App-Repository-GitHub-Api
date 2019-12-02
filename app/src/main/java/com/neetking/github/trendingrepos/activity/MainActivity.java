package com.neetking.github.trendingrepos.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neetking.github.trendingrepos.R;
import com.neetking.github.trendingrepos.adapter.RepositoriesAdapter;
import com.neetking.github.trendingrepos.api.RepositoryApiMaker;
import com.neetking.github.trendingrepos.api.RepositoryApiService;
import com.neetking.github.trendingrepos.model.RepositoryResponse;
import com.neetking.github.trendingrepos.utils.APIError;
import com.neetking.github.trendingrepos.utils.ErrorUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView = null;
    private CoordinatorLayout coordinatorLayout;
    public  RepositoriesAdapter adapter;
    RepositoryResponse repositoriesList;

    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = findViewById(R.id.container);
        recyclerView = findViewById(R.id.linear_recyclerview);
        progressBar = findViewById(R.id.repository_progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration); // Add divider between items
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentPage+=1;
                                fetchNextPage();
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }, 1000);

                    }
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });


        //checking for network connectivity
        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Network connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            fetchFirstPage();
                        }
                    });

            snackbar.show();
        } else {
            fetchFirstPage();
        }

    }


    private void prepareData(RepositoryResponse repositoriesList) {
        adapter = new RepositoriesAdapter(repositoriesList.getItems());
        recyclerView.setAdapter(adapter);
    }

    private void fetchFirstPage() {
        Map<String, String> data = new HashMap<>();
        data.put("q", "created:>2017-10-22");
        data.put("sort", "stars");
        data.put("order", "desc");
        RepositoryApiService apiService = new RepositoryApiMaker().getService();
        Call<RepositoryResponse>  repositoryListCall= apiService.getRepositoryList(data);
        repositoryListCall.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            " Sucessful",
                            Toast.LENGTH_SHORT).show();
                    repositoriesList = response.body();
                    prepareData(repositoriesList);

                } else {

                    APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Map<String, String> data = new HashMap<>();
        data.put("q", "created:>2017-10-22");
        data.put("sort", "stars");
        data.put("order", "desc");
        data.put("page", String.valueOf(currentPage));
        RepositoryApiService apiService = new RepositoryApiMaker().getService();
        Call<RepositoryResponse>  repositoryListCall= apiService.getRepositoryList(data);
        repositoryListCall.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            " Loading more  ",
                            Toast.LENGTH_SHORT).show();
                    RepositoryResponse repositoriesList2 = response.body();
                    repositoriesList.getItems().addAll(repositoriesList2.getItems());
                    Log.d("new size ",repositoriesList.getItems().size()+"");
                    adapter.notifyDataSetChanged();


                } else {

                    APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }

}
