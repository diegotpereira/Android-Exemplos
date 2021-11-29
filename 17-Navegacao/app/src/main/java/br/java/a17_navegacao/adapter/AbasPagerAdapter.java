package br.java.a17_navegacao.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Locale;

import br.java.a17_navegacao.R;
import br.java.a17_navegacao.SegundoNivelFragment;

public class AbasPagerAdapter extends FragmentPagerAdapter {

    String[] titulosAbas;
    TypedArray bgColors;
    TypedArray textColors;

    public AbasPagerAdapter(Context ctx, @NonNull FragmentManager fm) {
        super(fm);
        titulosAbas = ctx.getResources().getStringArray(R.array.secoes);
        bgColors = ctx.getResources().obtainTypedArray(R.array.cores_bg);
        textColors = ctx.getResources().obtainTypedArray(R.array.cores_texto);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        SegundoNivelFragment fragment = SegundoNivelFragment.novaInstancia(titulosAbas[position], bgColors.getColor(position, 0), textColors.getColor(position, 0));

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return titulosAbas[position].toUpperCase(l);
    }
}
