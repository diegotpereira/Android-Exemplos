package br.java.a18_persistencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTexto;
    TextView txtTexto;
    RadioGroup rgTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTexto = (EditText) findViewById(R.id.edtTexto);
        txtTexto = (TextView) findViewById(R.id.txtTexto);
        rgTipo = (RadioGroup) findViewById(R.id.rgTipo);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnLer).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean ler = false;

        if (view.getId() == R.id.btnLer) {
            ler = true;
        }

        int tipo = rgTipo.getCheckedRadioButtonId();

        if (ler) {
            switch (tipo) {
                case R.id.rbInterno:
                    carregarInterno();
                    break;

                case R.id.rbExternaPriv:
                    carregarDoSdCard(true);
                    break;

                case R.id.rbExternaPublic:
                    carregarDoSdCard(false);
                    break;
            }

        } else {
            switch (tipo) {
                case R.id.rbInterno:
                    salvarInterno();
                    break;

                case R.id.rbExternaPriv:
                    salvarNoSdCard(true);
                    break;

                case R.id.rbExternaPublic:
                    salvarNoSdCard(false);
                    break;
            }
        }
    }

    private void salvarInterno() {
        try {
            FileOutputStream fos = openFileOutput("arquivo.txt", Context.MODE_PRIVATE);
            salvar(fos);
        } catch (IOException e) {
            Log.e("NGVL", "Erro ao salvar arquivo", e);
        }
    }

    private void carregarInterno() {
        try {
            FileInputStream fis = openFileInput("arquivo.txt");
            carregar(fis);
        } catch (IOException e) {
            Log.e("NGVL", "Erro ao carregar arquivo", e);
        }
    }

    private void salvarNoSdCard(boolean privado) {
        boolean temPermissao = checarPermissao(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSAO_SDCARD);

        if (!temPermissao) {
            return;
        }

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File meuDir = getExternalDir(privado);

            try {
                if (!meuDir.exists()) {
                    meuDir.mkdir();
                }

                File arquivoTxt = new File(meuDir, "arquivo.txt");

                if (!arquivoTxt.exists()) {
                    arquivoTxt.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(arquivoTxt);
                salvar(fos);
             } catch (IOException e) {
                Log.d("NGVL", "Erro ao salvar arquivo", e);
            }
        } else {
            Log.e("NGVL", "Não é posível escrever no SD Card");
        }
    }

    private void carregarDoSdCard(boolean privado) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            File meuDir = getExternalDir(privado);

            if (meuDir.exists()) {
                File arquivoText = new File(meuDir, "arquivo.txt");

                if (arquivoText.exists()) {
                    try {
                        arquivoText.createNewFile();
                        FileInputStream fis = new FileInputStream(arquivoText);
                        carregar(fis);
                    } catch (IOException e) {
                        Log.d("NGVL", "Erro ao carregar arquivo", e);
                    }
                }
            }
        }  else {
            Log.e("NGVL", "SD Card Indisponível");
        }
    }

    private File getExternalDir(boolean privado) {
        if (privado) {

            return getExternalFilesDir(null);

        } else {

            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        }
    }

    private void salvar(FileOutputStream fos) throws IOException {
        String[] linhas = TextUtils.split(edtTexto.getText().toString(), "\n");
        PrintWriter writer = new PrintWriter(fos);

        for(String linha : linhas) {
            writer.println(linha);
        }
        writer.flush();
        writer.close();
        fos.close();
    }

    private void carregar(FileInputStream fis) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String linha;

        while ((linha =  reader.readLine()) != null) {
            if (sb.length() != 0) sb.append('\n');
            sb.append(linha);
        }

        reader.close();
        fis.close();
        txtTexto.setText(sb.toString());
    }

    public static final int PERMISSAO_SDCARD = 0;

    private boolean checarPermissao(String permissao, int requestcode) {
        if (ActivityCompat.checkSelfPermission(this, permissao) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissao)) {
                Toast.makeText(this, "Você tem que habilitar essa permissão para poder salvar o arquivo", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{permissao}, requestcode);

            return false;
        }

        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSAO_SDCARD: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissão concedida", Toast.LENGTH_SHORT).show();
                    
                } else {
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void abrirPreferencias(View v) {
        startActivity(new Intent(this, ConfigActivity.class));
    }

    public void lerPreferencias(View v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String cidade = prefs.getString(getString(R.string.pref_cidade), getString(R.string.pref_cidade_default));

        String redeSocial = prefs.getString(getString(R.string.pref_rede_social), getString(R.string.pref_rede_social_default));

        boolean mensagens = prefs.getBoolean(getString(R.string.pref_mensagens), false);

        String msg = String.format("%s = %s\n%s = %s\n%s = %s", getString(R.string.titulo_cidade), cidade, getString(R.string.titulo_rede_social), redeSocial, getString(R.string.titulo_mensagens), String.valueOf(mensagens));

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


    }
}