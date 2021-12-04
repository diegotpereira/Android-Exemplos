package br.java.a29_googleplus.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedHashMap;

import br.java.a29_googleplus.R;
import br.java.a29_googleplus.TelaAbasActivity;
import br.java.a29_googleplus.TelaPagerActivity;
import br.java.a29_googleplus.TelaSpinnerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrimeiroNivelFragment#novaInstancia} factory method to
 * create an instance of this fragment.
 */
public class PrimeiroNivelFragment extends Fragment
            implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EXTRA_TIPO = "mTipo";
    private String mTipo;
    private LinkedHashMap<String, Class> mAcoes;


    public static PrimeiroNivelFragment novaInstancia(String tipo) {
        Bundle parametros = new Bundle();
        parametros.putString(EXTRA_TIPO, tipo);
        PrimeiroNivelFragment fragment = new PrimeiroNivelFragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAcoes = new LinkedHashMap<String, Class>();
        mAcoes.put(getString(R.string.opcao_aba), TelaAbasActivity.class);
        mAcoes.put(getString(R.string.opcao_spinner), TelaSpinnerActivity.class);
        mAcoes.put(getString(R.string.opcao_pager), TelaPagerActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mTipo = getArguments().getString(EXTRA_TIPO);
        View layout = inflater.inflate(R.layout.fragment_primeiro_nivel, container, false);
        Button button = (Button) layout.findViewById(R.id.button);
        button.setOnClickListener(this);

        TextView textView = (TextView) layout.findViewById(R.id.textView);
        textView.setText(mTipo);
        return layout;
    }

    @Override
    public void onClick(View view) {
        Class classe = mAcoes.get(mTipo);
        startActivity(new Intent(getActivity(), classe));
    }
}