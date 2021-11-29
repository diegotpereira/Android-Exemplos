package br.java.a17_navegacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class TelaSpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String OPCAO_ATUAL = "opcaoAtual";
    Toolbar mToolbar;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.secoes));

        mSpinner = new Spinner(this);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.addView(mSpinner);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(OPCAO_ATUAL)) {
            mSpinner.setSelection(savedInstanceState.getInt(OPCAO_ATUAL));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(OPCAO_ATUAL, mSpinner.getSelectedItemPosition());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        exibirItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void exibirItem(int position) {
        ((TextView)mSpinner.getChildAt(0)).setTextColor(Color.WHITE);
        String[] titulosAbas = getResources().getStringArray(R.array.secoes);
        TypedArray bgColors = getResources().obtainTypedArray(R.array.cores_bg);
        TypedArray textColors = getResources().obtainTypedArray(R.array.cores_texto);

        SegundoNivelFragment fragment = SegundoNivelFragment.novaInstancia(titulosAbas[position], bgColors.getColor(position, 0), textColors.getColor(position, 0));

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag("tag");
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment, "tag");

        if (f != null) {
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        ft.commit();
    }
}