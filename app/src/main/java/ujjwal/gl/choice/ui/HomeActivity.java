package ujjwal.gl.choice.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ujjwal.gl.choice.R;
import ujjwal.gl.choice.adapter.MovieAdapter;
import ujjwal.gl.choice.callback.MovieCallback;
import ujjwal.gl.choice.constant.Constants;
import ujjwal.gl.choice.model.Movie;
import ujjwal.gl.choice.tmdb.MoviesRepository;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecMovies;
    MovieAdapter mMovieAdapter;
    List<Movie> mMovies;
    private MoviesRepository moviesRepository;
    ProgressBar mProgress;
    Button mPopularBtn, mNewBtn;
    String mType = "pop";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.WATCH_LATER.size()==0){
                    Snackbar.make(view, getString(R.string.not_selected), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    startActivity(new Intent(getApplicationContext(),FavouritesActivity.class));
                }

            }
        });

        mPopularBtn.setOnClickListener(this);
        mNewBtn.setOnClickListener(this);

        moviesRepository = MoviesRepository.getInstance();

        mRecMovies.setHasFixedSize(true);
        mRecMovies.setLayoutManager(new GridLayoutManager(this,2));

        mPopularBtn.setBackgroundResource(R.drawable.unselected_btn);
        mPopularBtn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        loadMovies();
    }

    private void initViews() {
        mProgress = findViewById(R.id.progress);
        mRecMovies = findViewById(R.id.rec_movies);
        mMovies = new ArrayList<>();
        mPopularBtn = findViewById(R.id.pop_btn);
        mNewBtn = findViewById(R.id.new_btn);

    }

    void loadMovies(){
        moviesRepository.getMovies(new MovieCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mMovieAdapter = new MovieAdapter(getApplicationContext(),movies);
                mRecMovies.setAdapter(mMovieAdapter);
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
            }
        },mType);
    }


    @Override
    public void onClick(View view) {
        if(view == mPopularBtn){
            mType = "pop";
            mProgress.setVisibility(View.VISIBLE);

            mPopularBtn.setBackgroundResource(R.drawable.unselected_btn);
            mPopularBtn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

            mNewBtn.setBackgroundResource(R.drawable.selected_btn);
            mNewBtn.setTextColor(ContextCompat.getColor(this,R.color.white));
            loadMovies();
        }
        if(view == mNewBtn){
            mType = "new";
            mProgress.setVisibility(View.VISIBLE);

            mNewBtn.setBackgroundResource(R.drawable.unselected_btn);
            mNewBtn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

            mPopularBtn.setBackgroundResource(R.drawable.selected_btn);
            mPopularBtn.setTextColor(ContextCompat.getColor(this,R.color.white));
            loadMovies();
        }
    }
}
