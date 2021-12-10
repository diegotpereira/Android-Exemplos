package br.java.a32_mapas.task;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;
import java.util.Locale;

public class BuscarLocalTask extends AsyncTaskLoader<List<Address>> {

    Context mContext;
    String mLocal;
    List<Address> mEnderecosEncontrados;


    public BuscarLocalTask(@NonNull Context activity, String local) {
        super(activity);
        mContext = activity;
        mLocal = local;
    }

    @Override
    protected void onStartLoading() {
        if (mEnderecosEncontrados == null) {
            forceLoad();
        } else {
            deliverResult(mEnderecosEncontrados);
        }
    }

    @Nullable
    @Override
    public List<Address> loadInBackground() {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        try {
            mEnderecosEncontrados = geocoder.getFromLocationName(mLocal, 10);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return mEnderecosEncontrados;
    }
}
