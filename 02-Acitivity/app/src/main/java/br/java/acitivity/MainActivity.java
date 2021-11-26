package br.java.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.java.acitivity.modelo.Cliente;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTexto = (EditText) findViewById(R.id.editText);

        Button botao = (Button) findViewById(R.id.botao);
        botao.setOnClickListener(this);

        Button btnTela2 = (Button) findViewById(R.id.botao2);
        btnTela2.setOnClickListener(this);

        Button btnTela2Parcelable = (Button) findViewById(R.id.botao3);
        btnTela2Parcelable.setOnClickListener(this);

        Log.i("NGVL", "Tela1::onCreate");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botao:
                String texto = edtTexto.getText().toString();

                Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
                break;

            case R.id.botao2:
                Intent intent = new Intent(this, Tela2Activity.class);
                intent.putExtra("nome", "Diego");
                intent.putExtra("idade", 39);
                startActivity(intent);
                break;

            case R.id.botao3:
                Cliente cliente = new Cliente(1 , "Admin");
                Intent intent2 = new Intent(this, Tela2Activity.class);
                intent2.putExtra("cliente", cliente);
                startActivity(intent2);
                break;
        }
    }
}