package com.mayank.drawerlayoutdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mayank.drawerlayout.Drawer;

public class MainActivity extends Activity {

    Drawer drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        drawer.setShadowingBackground(findViewById(R.id.dBackground));
        findViewById(R.id.app_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.expand();
            }
        });
    }
}
