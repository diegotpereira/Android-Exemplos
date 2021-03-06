package br.java.a38_multimidia.fragment;

import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.java.a38_multimidia.GerenciadorDeSom;
import br.java.a38_multimidia.R;


public class EfeitosSonorosFragment extends Fragment {

    private Button mBtnPlayMusica;
    private Button mBtnPlayEfeito;
    private Button mBtnStop;
    private GerenciadorDeSom mGerenciadorDeSom;

    private View.OnClickListener tratadorDosBotoes = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnPlayMusica:
                    mGerenciadorDeSom.tocarSom(0);
                    break;
                case R.id.btnPlayExplosao:
                    mGerenciadorDeSom.tocarSom(1);
                    break;
                case R.id.btnStop:
                    mGerenciadorDeSom.pararTodosOsSons();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_efeitos_sonoros, container, false);
        mBtnPlayMusica = (Button) layout.findViewById(R.id.btnPlayMusica);
        mBtnPlayEfeito = (Button) layout.findViewById(R.id.btnPlayExplosao);
        mBtnStop = (Button) layout.findViewById(R.id.btnStop);
        mBtnPlayMusica.setOnClickListener(tratadorDosBotoes);
        mBtnPlayEfeito.setOnClickListener(tratadorDosBotoes);
        mBtnStop.setOnClickListener(tratadorDosBotoes);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGerenciadorDeSom = GerenciadorDeSom.getInstance(getActivity());
        mGerenciadorDeSom.adicionarSom(R.raw.tema_mario);
        mGerenciadorDeSom.adicionarSom(R.raw.explosao);
    }

    @Override
    public void onPause() {
        super.onPause();
        mGerenciadorDeSom.liberarRecursos();
    }
}