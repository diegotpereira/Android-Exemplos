package br.java.a29_googleplus.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.java.a29_googleplus.R;

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

    // TODO: Rename and change types and number of parameters
    public static SegundoNivelFragment novaInstancia(
            String texto, int background, int textColor) {
        Bundle parametros = new Bundle();
        parametros.putString(EXTRA_TEXTO, texto);
        parametros.putInt(EXTRA_COR_BG, background);
        parametros.putInt(EXTRA_COR_TEXTO, textColor);

        SegundoNivelFragment fragment = new SegundoNivelFragment();
        fragment.setArguments(parametros);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle parametros = getArguments();
        String texto = parametros.getString(EXTRA_TEXTO);
        int bgColor = parametros.getInt(EXTRA_COR_BG);
        int textColor = parametros.getInt(EXTRA_COR_TEXTO);

        View layout = inflater.inflate(
                R.layout.fragment_segundo_nivel, container, false);

        TextView txt = (TextView) layout.findViewById(R.id.text_View);
        txt.setText(texto);
        txt.setTextColor(textColor);

        return layout;
    }
}