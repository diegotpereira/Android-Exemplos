package br.java.a38_multimidia;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;

public class DominandoCameraActivity extends Activity
         implements View.OnClickListener{

    private Camera mCamera;
    private VisualizacaoCamera mPreview;
    private MediaRecorder mMediaRecorder;
    private boolean mGravando;
    private boolean mTirouFoto;
    private Button mBtnCapturar;
    private File mCaminhoArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominando_camera);

        Uri uri = getIntent().getParcelableExtra(MediaStore.EXTRA_OUTPUT);

        if (uri != null) {
            mCaminhoArquivo = new File(uri.getPath());
        }

        mBtnCapturar = (Button) findViewById(R.id.btnCapturar);
        mBtnCapturar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cameraDisponivel()) {

            abrirCamera();mPreview = new VisualizacaoCamera(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.previewCamera);
            preview.addView(mPreview);
        }
    }

    @Override
    public void onClick(View view) {
        String action = getIntent().getAction();

        if (action.equals(MediaStore.ACTION_IMAGE_CAPTURE)) {
            tirarFoto();

        } else if (action.equals(MediaStore.ACTION_VIDEO_CAPTURE)) {
            gravarVideo();
        }
    }

    private boolean cameraDisponivel() {

        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void abrirCamera() {

    }

    private void tirarFoto() {

    }

    private void gravarVideo() {

    }
}