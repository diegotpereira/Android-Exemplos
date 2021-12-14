package br.java.a38_multimidia.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;

import br.java.a38_multimidia.R;
import br.java.a38_multimidia.utils.Util;


public class CameraFotoFragment extends Fragment implements
        View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener{

    File mCaminhoFoto;
    ImageView mImageViewFoto;
    CarregarImageTask mTask;
    int mLarguraImagem;
    int mAlturaImagem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String caminhoFoto = Util.getUltimaMidia(getActivity(), Util.MIDIA_FOTO);

        if (caminhoFoto != null) {

            mCaminhoFoto = new File(caminhoFoto);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(
                R.layout.fragment_camera_foto, container, false);
        layout.findViewById(R.id.btnFoto).setOnClickListener(this);
        layout.findViewById(R.id.btnWallpaper).setOnClickListener(this);

        mImageViewFoto = (ImageView)  layout.findViewById(R.id.imgFoto);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(this);

        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == Util.REQUESTCODE_FOTO) {
            carregarImagem();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGlobalLayout() {

    }

    private void carregarImagem() {

        if (mCaminhoFoto != null && mCaminhoFoto.exists()) {
            if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
                mTask = new CarregarImageTask();
                mTask.execute();
            }
        }
    }

    class CarregarImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return Util.carregarImagem(
                    mCaminhoFoto,
                    mLarguraImagem,
                    mAlturaImagem);
        }
    }
}