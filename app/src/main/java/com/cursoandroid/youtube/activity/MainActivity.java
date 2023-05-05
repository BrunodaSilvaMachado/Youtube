package com.cursoandroid.youtube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.adapter.VideoAdapter;
import com.cursoandroid.youtube.api.YoutubeService;
import com.cursoandroid.youtube.config.RetrofitConfig;
import com.cursoandroid.youtube.config.YoutubeConfig;
import com.cursoandroid.youtube.fragment.PlayerFragment;
import com.cursoandroid.youtube.model.Item;
import com.cursoandroid.youtube.model.Results;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private SearchView searchView;
    private List<Item> videoList = new ArrayList<>();
    private Retrofit retrofit;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        retrofit = RetrofitConfig.getRetrofit();
        recyclerVideos = findViewById(R.id.recyclerVideos);
        recuperarVideos("");
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull @NotNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        configurarSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void recuperarVideos(String pesquisa){
        String q = pesquisa.replaceAll(" ", "+");
        YoutubeService youtubeService =  retrofit.create(YoutubeService.class);
        youtubeService.recuperarVideos("snippet","date","20",
                        YoutubeConfig.KEY,YoutubeConfig.CANAL_ID,q)
                .enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(@NotNull Call<Results> call, @NotNull Response<Results> response) {
                        Results results = response.body();
                        if (results != null) {
                            videoList = results.items;
                            configurarRecyclerView();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Results> call, @NotNull Throwable t) {}
                });

    }

    private void configurarSearchView(){
        if (searchView == null){
            return;
        }
        searchView.setQueryHint(getString(R.string.search));
        searchView.setBackgroundColor(0xffffff);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            recuperarVideos("");
            return false;
        });
    }

    private void configurarRecyclerView(){
        VideoAdapter videoAdapter = new VideoAdapter(this, videoList);
        videoAdapter.setOnItemClickListener(video->{
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragmentContainer, PlayerFragment.newInstance(video),"PlayerFragment").commit();
            /*String idVideo = video.id.videoId;
            Intent i = new Intent(MainActivity.this, PlayerActivity.class);
            i.putExtra("VIDEO_ID",idVideo);
            startActivity(i);*/
        });
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerVideos.setAdapter(videoAdapter);
    }
}