package br.java.a25_mp3service.binder;

import android.os.Binder;

import br.java.a25_mp3service.service.Mp3Service;

public class Mp3Binder extends Binder {

    private Mp3Service mServico;

    public Mp3Binder(Mp3Service s) {
        mServico = s;
    }

    public Mp3Service getServico() {
        return mServico;
    }
}
