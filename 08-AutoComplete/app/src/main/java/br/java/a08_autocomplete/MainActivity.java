package br.java.a08_autocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import br.java.a08_autocomplete.adapter.MeuAutoCompleteAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> cidade = new ArrayList<String>();
        cidade.add("Porto Alegre");
        cidade.add("Canoas");
        cidade.add("Caxias do Sul");
        cidade.add("São Paulo");
        cidade.add("Santos");
        cidade.add("Rio de Janeiro");

        MeuAutoCompleteAdapter adapter = new MeuAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, cidade);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setAdapter(adapter);
    }
}