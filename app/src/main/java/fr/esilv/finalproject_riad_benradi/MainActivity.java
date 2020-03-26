package fr.esilv.finalproject_riad_benradi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapater.OnItemListener {

    private KitsuService service;
    private TextView textViewResult;
    private RecyclerView myRecyclerView;
    private MyAdapater myAdapter;
    private List<KitsuSearchData> results;
    private MyAdapater.OnItemListener onItemListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(KitsuService.class);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>=2)
                {animeSearch(newText);}
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void animeSearch(String anime) {
        Call<KitsuSearchResponse> call = service.groupList(anime);
        call.enqueue(new Callback<KitsuSearchResponse>() {
            @Override
            public void onResponse(Call<KitsuSearchResponse> call, Response<KitsuSearchResponse> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText(("Code" + response.code()));
                    return;
                }
                KitsuSearchResponse kitsuSearchResponse = response.body();
                results = kitsuSearchResponse.getData();
                myAdapter = new MyAdapater(results,onItemListener);
                myRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<KitsuSearchResponse> call, Throwable t) {
                textViewResult.setText((t.getMessage()));
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        KitsuSearchData data = results.get(position);

        Intent intent = new Intent(this,DetailsActivity.class);
        String image = data.getAttributes().getPosterImage().getLarge();
        String youtube_id = data.getAttributes().getYoutubeVideoId();
        String mTitle = data.getAttributes().getTitles().getEn();
        String mSynopsis = data.getAttributes().getSynopsis();
        String slug = data.getAttributes().getSlug();

        intent.putExtra("image",image);
        intent.putExtra("youtube_id",youtube_id);
        intent.putExtra("title",mTitle);
        intent.putExtra("synopsis",mSynopsis);
        intent.putExtra("slug",slug);
        startActivity(intent);

    }
}