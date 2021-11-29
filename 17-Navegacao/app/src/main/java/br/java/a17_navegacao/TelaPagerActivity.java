package br.java.a17_navegacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import br.java.a17_navegacao.adapter.AbasPagerAdapter;

public class TelaPagerActivity extends AppCompatActivity {

    AbasPagerAdapter abasPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pager);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        abasPagerAdapter = new AbasPagerAdapter(this, getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(abasPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomPageTransformer());
    }
}