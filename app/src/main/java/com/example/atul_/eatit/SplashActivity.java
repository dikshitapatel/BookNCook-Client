package com.example.atul_.eatit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by atul_ on 16/03/2018.
 */

public class SplashActivity extends Activity {

    private ImageView iv;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);

        iv=(ImageView)findViewById(R.id.logo_id);


        Animation myAnim= AnimationUtils.loadAnimation(this, R.anim.splash);
        iv.startAnimation(myAnim);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
