package com.example.pals.memegenerator;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements TopFragment.TopSectionListener{

    BottomFragment bottomFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomFragment = (BottomFragment) getSupportFragmentManager().findFragmentById(R.id.fragment5);
    }

    @Override
    public void createMeme(String top, String bottom) {
        bottomFragment.setMemeText(top,bottom);
    }

    @Override
    public void createMemeImg(ImageView img) {
        bottomFragment.setMemeImage(img);
    }
}
