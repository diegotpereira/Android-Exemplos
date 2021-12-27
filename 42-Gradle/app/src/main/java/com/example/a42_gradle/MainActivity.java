package com.example.a42_gradle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.minhalib.LibActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chamarLibActivity(View v) {
        startActivity(new Intent(this, LibActivity.class));
    }

    public void chamarSegundaActivity(View v) {
        startActivity(new Intent(this, SegundaActivity.class));
    }

}