package br.java.a41_sensores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.concurrent.TimeUnit;

public class ThreadJogo extends Thread{

    private boolean mExecutando;
    private Bolinha mBolinha;
    private SurfaceHolder mHolder;
    private Bitmap mImgBackground;

    public ThreadJogo(Context context, Bolinha bolinha, SurfaceHolder holder) {

        mBolinha = bolinha;
        mHolder = holder;
        mImgBackground = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.grass);
    }

    @Override
    public void run() {
        super.run();

        while(mExecutando) {
            mBolinha.calcularFisica();
            pintar();

            try {
                TimeUnit.MILLISECONDS.sleep(16);

            } catch (InterruptedException e) {
                mExecutando = false;
            }
        }
    }

    public void parar() {
        mExecutando = false;
        interrupt();
    }

    private void pintar() {

        Canvas c = null;

        try {
            c = mHolder.lockCanvas();

            if (c != null) {

                Rect rect = new Rect(0, 0, c.getWidth(), c.getHeight());
                c.drawBitmap(mImgBackground, null, rect, null);
                c.drawBitmap(mBolinha.getImagemBolinha(),
                        mBolinha.getX(), mBolinha.getY(), null);
            }
        } finally {

            if (c != null) {
                mHolder.unlockCanvasAndPost(c);
            }
        }
    }
}
