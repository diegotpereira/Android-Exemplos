package br.java.a27_notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalheActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTO = "texto";
    public static final String EXTRA_RESPOSTA_VOZ = "resposta_voz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        String string = getIntent().getStringExtra(EXTRA_TEXTO);

        String respostaVoz = obterTextoFalado(getIntent());
        if (respostaVoz != null) {
            string += ": " + respostaVoz;
        }

        TextView txt = (TextView) findViewById(R.id.txtDetalhe);
        txt.setText(string);
    }

    private String obterTextoFalado(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            return  remoteInput.getCharSequence(EXTRA_RESPOSTA_VOZ).toString();
        }

        return null;
    }
}