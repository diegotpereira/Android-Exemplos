package br.java.a39_animacoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
         implements View.OnClickListener, Animation.AnimationListener {

    private static final int DURACAO_ANIMACAO = 1000;
    private Spinner mSpnAnimation;
    private Spinner mSpnInterpolator;
    private Button mButtonPlay;
    private ImageView mImg;
    private Animation[] mAnimacoes;
    private Interpolator[] mInterpolators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpnAnimation = (Spinner) findViewById(R.id.spnAnimacoes);
        mSpnInterpolator = (Spinner) findViewById(R.id.spnInterpolator);
        mImg =(ImageView) findViewById(R.id.imageView);
        mButtonPlay = (Button) findViewById(R.id.btnPlay);

        mButtonPlay.setOnClickListener(this);

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Animacoes3Activity.class);
                Bundle animacao = ActivityOptionsCompat.makeCustomAnimation(
                        MainActivity.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
                ActivityCompat.startActivity(MainActivity.this, it, animacao);
            }
        });
        initInterpolators();
        initAnimacoes();
    }

    @Override
    public void onClick(View view) {
        executarAnimacao();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void initAnimacoes() {
        mAnimacoes = new Animation[mSpnAnimation.getCount()];
        mAnimacoes[0] = AnimationUtils.loadAnimation(this, R.anim.transparencia);
        mAnimacoes[1] = AnimationUtils.loadAnimation(this, R.anim.rotacao);
        mAnimacoes[2] = AnimationUtils.loadAnimation(this, R.anim.escala);
        mAnimacoes[3] = AnimationUtils.loadAnimation(this, R.anim.translacao);
        mAnimacoes[4] = AnimationUtils.loadAnimation(this, R.anim.tudo_junto);
    }

    private void initInterpolators() {
        mInterpolators = new Interpolator[mSpnInterpolator.getCount()];
        mInterpolators[0] = new AccelerateDecelerateInterpolator();
        mInterpolators[1] = new AccelerateInterpolator();
        mInterpolators[2] = new AnticipateInterpolator();
        mInterpolators[3] = new AnticipateOvershootInterpolator();
        mInterpolators[4] = new BounceInterpolator();
        mInterpolators[5] = new CycleInterpolator(2);
        mInterpolators[6] = new DecelerateInterpolator();
        mInterpolators[7] = new LinearInterpolator();
        mInterpolators[8] = new OvershootInterpolator();
    }

    private void executarAnimacao() {
        Interpolator interpolator =
                mInterpolators[mSpnInterpolator.getSelectedItemPosition()];
        Animation animation = mAnimacoes[mSpnAnimation.getSelectedItemPosition()];
        animation.setInterpolator(interpolator);
        animation.setAnimationListener(this);
        mImg.requestLayout();
        mImg.setAnimation(animation);
        animation.start();
        mButtonPlay.setEnabled(false);
    }
}