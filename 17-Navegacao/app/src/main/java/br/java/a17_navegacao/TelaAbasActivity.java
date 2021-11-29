package br.java.a17_navegacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import br.java.a17_navegacao.adapter.AbasPagerAdapter;

public class TelaAbasActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_abas);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        AbasPagerAdapter pagerAdapter = new AbasPagerAdapter(this, getSupportFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}