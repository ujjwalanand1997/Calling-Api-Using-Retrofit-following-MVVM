package ujjwal.gl.choice.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.request.target.ThumbnailImageViewTarget;

import java.util.Timer;
import java.util.TimerTask;

import ujjwal.gl.choice.R;
import ujjwal.gl.choice.preferences.Preference;

import static ujjwal.gl.choice.constant.Constants.WATCH_LATER;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }


        /**Here I am checking for if user has set any watch later movies
         * if selected, It can be seen in watch later activity
         */

        if(Preference.getSharedPref(this,"watch")!=null){
            String[] strings = (Preference.getSharedPref(this,"watch")).split("\n");
            for(String s : strings){
                WATCH_LATER.add(s);
            }

            WATCH_LATER.remove(WATCH_LATER.size()-1);
        }

        //
        /**timer to schedule start home in 2 secs
         *
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        },2000);
    }

    /**
     * Managing on back button pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
