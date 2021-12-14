package br.java.a38_widgets.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import br.java.a38_widgets.FavoritosWidget;

public class FavoritosService extends Service {

    private String[] sites = {
            "github.com/diegotpereira",
            "developer.android.com",
    };

    private Map<Integer, Integer> widgetStates;

    @Override
    public void onCreate() {
        super.onCreate();
        widgetStates = new HashMap<Integer, Integer>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String acao = intent.getStringExtra(FavoritosWidget.EXTRA_ACAO);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
