package ujjwal.gl.choice.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import ujjwal.gl.choice.R;

import static ujjwal.gl.choice.constant.Constants.WATCH_LATER;

public class FavouritesActivity extends AppCompatActivity {

    TextView mWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Just a text view to show line by line the movies to watch later
         */
        mWatch = findViewById(R.id.watch);
        StringBuilder sb = new StringBuilder();
        for(String s : WATCH_LATER){
            sb.append(s).append("\n");
        }
        mWatch.setText(sb.toString());
    }

}
