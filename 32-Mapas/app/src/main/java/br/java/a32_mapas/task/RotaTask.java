package br.java.a32_mapas.task;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.java.a32_mapas.http.RotaHttp;

public class RotaTask extends AsyncTaskLoader<List<LatLng>> {

    List<LatLng> mRota;
    LatLng mOrigem;
    LatLng mDestino;


    public RotaTask(@NonNull Context context, LatLng orig, LatLng dest) {
        super(context);
        mOrigem = orig;
        mDestino = dest;
    }

    @Override
    protected void onStartLoading() {
        if (mRota == null) {
            forceLoad();

        } else {
            deliverResult(mRota);
        }
    }

    @Nullable
    @Override
    public List<LatLng> loadInBackground() {
        mRota = RotaHttp.carregarRota(mOrigem, mDestino);
        return mRota;
    }
}
