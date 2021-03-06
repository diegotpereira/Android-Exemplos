package br.java.activityresult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_ESTADO = 1;
    private static final String STATE_ESTADO = "estado";

    Button botaoEstado;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoEstado = (Button) findViewById(R.id.btn_estado);
        botaoEstado.setOnClickListener(this);

        if (savedInstanceState != null) {
            estado = savedInstanceState.getString(STATE_ESTADO);

            if (estado != null) botaoEstado.setText(estado);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, TelaSelecaoActivity.class);
        intent.putExtra(TelaSelecaoActivity.EXTRA_ESTADO, estado);
        startActivityForResult(intent, REQUEST_ESTADO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_ESTADO) {
            estado = data.getStringExtra(TelaSelecaoActivity.EXTRA_RESULTADO);

            if (estado != null) {
                botaoEstado.setText(estado);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_ESTADO, estado);
    }
}