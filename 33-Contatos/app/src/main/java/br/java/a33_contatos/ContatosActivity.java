package br.java.a33_contatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.java.a33_contatos.fragment.ContatoFragment;
import br.java.a33_contatos.fragment.ListaContatosFragment;

import static android.Manifest.permission.READ_CONTACTS;

public class ContatosActivity extends AppCompatActivity {

    private static String EXTRA_PERMISSAO = "pediu";
    private static final int REQUEST_LER_CONTATO = 1;
    private boolean mPediuPermissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        if (savedInstanceState != null) {
            mPediuPermissao = savedInstanceState.getBoolean(EXTRA_PERMISSAO);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_PERMISSAO, mPediuPermissao);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

            if (!mPediuPermissao) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        REQUEST_LER_CONTATO);

                mPediuPermissao = true;
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LER_CONTATO) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Você precisa aceitar as permissões.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        }

        mPediuPermissao = false;
    }

    private void init() {
        if (getSupportFragmentManager().findFragmentByTag("contatos") == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new ListaContatosFragment(), "contatos")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_adicionar) {
            ContatoFragment cf = new ContatoFragment();
            cf.show(getSupportFragmentManager(), "adic_contato");
        }
        return super.onOptionsItemSelected(item);
    }
}