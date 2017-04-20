package pl.dupesko.dupesko.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import pl.dupesko.dupesko.R;

public class SplashActivity extends Activity    {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
