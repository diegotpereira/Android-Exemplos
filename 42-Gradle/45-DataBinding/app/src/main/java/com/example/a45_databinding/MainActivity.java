package com.example.a45_databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.a45_databinding.modelo.TratadorMagico;
import com.example.a45_databinding.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        usuario = new Usuario("Fulano", "DeTal");
        binding.setUsuario(usuario);
        binding.setTratador(new TratadorMagico());

        EditText edtNome = (EditText) findViewById(R.id.edtNome);
        edtNome.addTextChangedListener(new AoMudarTexto(R.id.edtNome));

        EditText edtSobrenome = (EditText) findViewById(R.id.edtSobrenome);
        edtSobrenome.addTextChangedListener(new AoMudarTexto(R.id.edtSobrenome));
    }

    class AoMudarTexto implements TextWatcher {

        private int id;

        public AoMudarTexto(int id) {}

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            switch (id) {
                case R.id.edtNome:
                    usuario.nome.set(s.toString());
                    break;

                case R.id.edtSobrenome:
                    usuario.sobrenome.set(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}