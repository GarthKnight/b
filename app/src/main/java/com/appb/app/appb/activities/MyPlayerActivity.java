package com.appb.app.appb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.appb.app.appb.R;
import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;
import com.halilibo.bettervideoplayer.subtitle.CaptionsView;

/**
 * Created by 1 on 15.03.2017.
 */

public class MyPlayerActivity extends AppCompatActivity {

    private BetterVideoPlayer bvp;
    private String TAG = "BetterSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_player);

        findViewById(R.id.fulscreen_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPlayerActivity.this, FullscreenActivity.class));
            }
        });


    }
}
