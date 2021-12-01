package br.java.a20_sqlite;

import android.app.Activity;
import android.os.Bundle;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import br.java.a20_sqlite.modelo.Hotel;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link HotelDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelDialogFragment extends DialogFragment
         implements TextView.OnEditorActionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DIALOG_TAG = "editDialog";
    private static final String EXTRA_HOTEL = "hotel";

    private EditText txtNome;
    private EditText txtEndereco;
    private RatingBar rtbEstrelas;
    private Hotel mHotel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hotel Parameter 1.
     * @return A new instance of fragment HotelDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelDialogFragment newInstance(Hotel hotel) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_HOTEL, hotel);
        HotelDialogFragment dialog = new HotelDialogFragment();
        dialog.setArguments(parametros);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotel = (Hotel) getArguments().getSerializable(EXTRA_HOTEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(
                R.layout.fragment_dialog_hotel, container, false);

        txtNome = (EditText) layout.findViewById(R.id.txtNome);
        txtNome.requestFocus();
        txtEndereco = (EditText) layout.findViewById(R.id.txtEndereco);
        txtEndereco.setOnEditorActionListener(this);
        rtbEstrelas = (RatingBar) layout.findViewById(R.id.rtbEstrelas);

        if (mHotel != null) {
            txtNome.setText(mHotel.nome);
            txtEndereco.setText(mHotel.endereco);
            rtbEstrelas.setRating(mHotel.estrelas);
        }
        // Exibe o teclado virtual ao exibir o Dialog
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.acao_novo);
        return layout;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            Activity activity = getActivity();
            if (activity instanceof AoSalvarHotel) {
                if (mHotel == null) {
                    mHotel = new Hotel(
                            txtNome.getText().toString(),
                            txtEndereco.getText().toString(),
                            rtbEstrelas.getRating());
                } else {
                    mHotel.nome = txtNome.getText().toString();
                    mHotel.endereco = txtEndereco.getText().toString();
                    mHotel.estrelas = rtbEstrelas.getRating();
                }

                AoSalvarHotel listener = (AoSalvarHotel) activity;
                listener.salvouHotel(mHotel);

                // Fechar Dialog
                dismiss();

                return true;
            }
        }
        return false;
    }

    public  void abrir(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    public interface AoSalvarHotel {
        void salvouHotel(Hotel hotel);
    }
}