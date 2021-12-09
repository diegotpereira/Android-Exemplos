package br.java.a31_admob.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import br.java.a31_admob.http.HotelHttp;

public class HotelIntentService extends IntentService {

    public static final String ACAO_SINCRONIZAR = "sincronizar";
    public static final String EXTRA_SUCESSO   = "sucesso";
    /**
     * @param name
     * @deprecated
     */
    public HotelIntentService(String name) {
        super("HotelIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            HotelHttp hotelHttp = new HotelHttp(this);
            Intent it = new Intent(ACAO_SINCRONIZAR);
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            try {
                hotelHttp.sincronizar();
                it.putExtra(EXTRA_SUCESSO, true);
            } catch (Exception e) {
                it.putExtra(EXTRA_SUCESSO, false);
                e.printStackTrace();
            } finally {
                lbm.sendBroadcast(it);
            }
        }
    }
}
