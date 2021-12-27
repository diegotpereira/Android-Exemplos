package br.java.a42_gradle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.java.a42_gradle.minhalib.LibActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chamarLiActivity(View v) {
        startActivity(new Intent(this, LibActivity.classs));
    }

    public void chamarSegundaActivity(View v) {
        startActivity(new Intent(this, SegundaActivity.class));
    }
}