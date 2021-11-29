package br.java.a17_navegacao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegundoNivelFragment#novaInstancia} factory method to
 * create an instance of this fragment.
 */
public class SegundoNivelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EXTRA_TEXTO = "texto";
    private static final String EXTRA_COR_BG = "corBg";
    private static final String EXTRA_COR_TEXTO = "corTexto";


    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public SegundoNivelFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SegundoNivelFragment novaInstancia(String texto, int  background, int textColor) {

        Bundle params = new Bundle();
        params.putString(EXTRA_TEXTO, texto);
        params.putInt(EXTRA_COR_BG, background);
        params.putInt(EXTRA_COR_TEXTO, textColor);
        SegundoNivelFragment fragment = new SegundoNivelFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle params = getArguments();
        String texto = params.getString(EXTRA_TEXTO);
        int bgColor = params.getInt(EXTRA_COR_BG);
        int textColor = params.getInt(EXTRA_COR_TEXTO);

        View layout = inflater.inflate(R.layout.fragment_segundo_nivel, container, false);
        layout.setBackgroundColor(bgColor);
        TextView txt = (TextView) layout.findViewById(R.id.textView);
        txt.setText(texto);
        txt.setTextColor(textColor);

        return layout;
    }
}