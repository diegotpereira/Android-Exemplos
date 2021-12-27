package br.java.a44_enghaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.java.a44_enghaw.db.DiscoDb;
import br.java.a44_enghaw.http.DiscoHttp;
import br.java.a44_enghaw.modelo.Disco;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DetalheActivity extends AppCompatActivity {
    public static final String EXTRA_DISCO = "disco";

    @Bind(R.id.fabFavorito)
    FloatingActionButton mFabFavorito;
    @Bind(R.id.imgCapa)
    ImageView mImgCapa;
    @Bind(R.id.txtTitulo)
    TextView mTxtTitulo;
    @Bind(R.id.txtAno)
    TextView mTxtAno;
    @Bind(R.id.txtGravadora)
    TextView mTxtGravadora;
    @Bind(R.id.txtFormacao)
    TextView mTxtFormacao;
    @Bind(R.id.txtMusicas)
    TextView mTxtMusicas;

    @Nullable
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @Nullable
    @Bind(R.id.appBar)
    AppBarLayout mAppBar;
    @Nullable
    @Bind(R.id.collapseToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    Target mPicassoTarget;
    DiscoDb mDiscoDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        ButterKnife.bind(this);

        Disco disco = (Disco)getIntent().getSerializableExtra(EXTRA_DISCO);
        preencherCampos(disco);

        configurarBarraDeTitulo(disco.titulo);

        carregarCapa(disco);

        configurarAnimacaoEntrada();

        mDiscoDb = new DiscoDb(this);

        configurarFab(disco);
    }

    private void carregarCapa(Disco disco) {
        if (mPicassoTarget == null) {
            mPicassoTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mImgCapa.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    iniciarAnimacaoDeEntrada(mCoordinator);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
        }
        Picasso.with(this)
                .load(DiscoHttp.BASE_URL + disco.capaGrande)
                .into(mPicassoTarget);
    }

    private void preencherCampos(Disco disco) {
        mTxtTitulo.setText(disco.titulo);
        mTxtAno.setText(String.valueOf(disco.ano));
        mTxtGravadora.setText(disco.gravadora);

        StringBuilder sb = new StringBuilder();

        for(String integrante  : disco.formacao) {
            if (sb.length() != 0 ) sb.append('\n');
            sb.append(integrante);
        }

        mTxtFormacao.setText(sb.toString());
        sb = new StringBuilder();
        for(int i = 0; i < disco.faixas.length; i++) {
            if (sb.length() != 0) sb.append('\n');
            sb.append(i + 1).append(". ").append(disco.faixas[i]);
        }

        mTxtMusicas.setText(sb.toString());
    }

    private void configurarBarraDeTitulo(String titulo) {
        setSupportActionBar(mToolbar);
        if (mAppBar != null) {
            if (mAppBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams ) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                lp.height = getResources().getDisplayMetrics().widthPixels;
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mCollapsingToolbarLayout != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mCollapsingToolbarLayout.setTitle(titulo);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void configurarAnimacaoEntrada() {
        ViewCompat.setTransitionName(mImgCapa, "capa");
        ViewCompat.setTransitionName(mTxtTitulo, "titulo");
        ViewCompat.setTransitionName(mTxtAno, "ano");
        ActivityCompat.postponeEnterTransition(this);
    }

    private void iniciarAnimacaoDeEntrada(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        ActivityCompat.startPostponedEnterTransition(DetalheActivity.this);

                        return true;
                    }
                }
        );
    }

    private void definirCores(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(@Nullable Palette palette) {
                int vibrantColor = palette.getVibrantColor(Color.BLACK);
                int darkVibrantColor = palette.getDarkVibrantColor(Color.BLACK);
                int darkMutedColor = palette.getDarkMutedColor(Color.BLACK);
                int lightMutedColor = palette.getLightMutedColor(Color.WHITE);

                mTxtTitulo.setTextColor(vibrantColor);
                if (mAppBar != null) {
                    mAppBar.setBackgroundColor(vibrantColor);
                } else {
                    mToolbar.setBackgroundColor(Color.TRANSPARENT);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(darkMutedColor);
                }

                if (mCollapsingToolbarLayout != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
                }
                mCoordinator.setBackgroundColor(lightMutedColor);
                iniciarAnimacaoDeEntrada(mCoordinator);
            }
        });
    }

    private void configurarFab(final Disco disco) {
        boolean favorito = mDiscoDb.favorito(disco);
        mFabFavorito.setImageDrawable(getFabIcone(favorito));
        mFabFavorito.setBackgroundTintList(getFabBackground(favorito));
        mFabFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean favorito = mDiscoDb.favorito(disco);
                if (favorito) {
                    mDiscoDb.excluir(disco);
                } else {
                    mDiscoDb.inserir(disco);
                }

                mFabFavorito.setImageDrawable(getFabIcone(!favorito));
                mFabFavorito.setBackgroundTintList(getFabBackground(!favorito));
                animar(!favorito);
                ((DiscoApp) getApplication()).getBus().post(new DiscoEvento(disco));
            }
        });
    }

    private Drawable getFabIcone(boolean favorito) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResourcesCompat.getDrawable(
                    getResources(),
                    favorito ? R.drawable.ic_cancel_anim : R.drawable.ic_check_anim,
                    getTheme());
        } else {

            return getResources().getDrawable(
                    favorito ? R.drawable.ic_cancel : R.drawable.ic_check);
        }
    }

    private ColorStateList getFabBackground(boolean favorito) {
        return getResources().getColorStateList(favorito ?
                R.color.bg_fab_cancel : R.color.bg_fab_favorito);
    }

    @Override
    public void onBackPressed() {
        mFabFavorito.animate().scaleX(0).scaleY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                DetalheActivity.super.onBackPressed();
            }
        }).start();
    }

    private void animar(boolean favorito){
        mFabFavorito.setBackgroundTintList(getFabBackground(favorito));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getFabIcone(!favorito);
            mFabFavorito.setImageDrawable(avd);
            avd.start();
        } else {
            mFabFavorito.setImageDrawable(getFabIcone(favorito));
        }
    }
}