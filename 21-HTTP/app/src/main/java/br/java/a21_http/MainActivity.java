package br.java.a21_http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import br.java.a21_http.fragment.LivrosGridFragment;
import br.java.a21_http.fragment.LivrosListFragment;

public class MainActivity extends AppCompatActivity {

    LivroPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new LivroPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mPagerAdapter);
    }

    class LivroPagerAdapter extends FragmentPagerAdapter {
        LivrosListFragment mList;
        LivrosGridFragment mGrid;

        public LivroPagerAdapter(FragmentManager fm) {
            super(fm);
            mList = new LivrosListFragment();
            mGrid = new LivrosGridFragment();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            return (position == 0) ? mList : mGrid;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "Lista" : "Grid";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}