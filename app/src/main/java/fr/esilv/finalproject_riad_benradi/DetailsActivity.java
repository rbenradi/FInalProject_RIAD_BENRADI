package fr.esilv.finalproject_riad_benradi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class DetailsActivity extends YouTubeBaseActivity {

    private List<KitsuSearchData> links;
    private String streaming_file, title, synopsis, image, youtube_id, slug,search;
    private String wiki = "http://en.wikipedia.org/wiki/";
    private String anime_website = "https://9anime.to/search?keyword=";
    private TextView textview_title, textview_synopsis;
    private ImageView imageView;
    private Button wikipedia, streaming;
    private KitsuService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();

        title = intent.getStringExtra("title");
        synopsis = intent.getStringExtra("synopsis");
        image = intent.getStringExtra("image");
        youtube_id = intent.getStringExtra("youtube_id");
        slug = intent.getStringExtra("slug");

        textview_title = (TextView) findViewById(R.id.title2);
        textview_synopsis = (TextView) findViewById(R.id.synopsis2);
        imageView = (ImageView) findViewById(R.id.imageView2);

        textview_title.setText(title);
        textview_synopsis.setText(synopsis);
        Glide.with(this).load(image).into(imageView);

        final YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);
        Button btnPLAY = (Button) findViewById(R.id.btnPlay);
        final YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtube_id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        btnPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
            }
        });

        wikipedia = (Button) findViewById(R.id.wikipedia);
        wikipedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wiki += title;
                Uri link = Uri.parse(wiki);
                Intent intent1 = new Intent(Intent.ACTION_VIEW).setData(link);
                startActivity(intent1);
            }
        });

        streaming = (Button) findViewById(R.id.streaming_file);
        streaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anime_website += title;
                Uri link = Uri.parse(anime_website);
                Intent intent2 = new Intent(Intent.ACTION_VIEW).setData(link);
                startActivity(intent2);
            }
        });
    }


}
