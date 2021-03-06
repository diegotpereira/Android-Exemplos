package br.java.a19_provider;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import br.java.a19_provider.modelo.Hotel;

public class HotelDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private static final String DIALOG_TAG = "editDialog";
    private static final String EXTRA_HOTEL = "hotel";

    private EditText txtNome;
    private EditText txtEndereco;
    private RatingBar rtbEstrelas;
    private Hotel mHotel;

    public static HotelDialogFragment newInstance(Hotel hotel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_HOTEL, hotel);

        HotelDialogFragment dialog = new HotelDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotel = (Hotel) getArguments().getSerializable(EXTRA_HOTEL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dialog_hotel, container, false);

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
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.acao_novo);

        return layout;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            Activity activity = getActivity();
            if (activity instanceof AoSalvarHotel) {
                if (mHotel == null) {
                    mHotel = new Hotel(txtNome.getText().toString(), txtEndereco.getText().toString(), rtbEstrelas.getRating());

                } else {
                    mHotel.nome = txtNome.getText().toString();
                    mHotel.endereco = txtEndereco.getText().toString();
                    mHotel.estrelas = rtbEstrelas.getRating();
                }

                AoSalvarHotel listener = (AoSalvarHotel) activity;
                listener.salvouHotel(mHotel);

                // Fechar dialog
                dismiss();

                return true;
            }
        }
        return false;
    }

    public void abrir(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    public interface AoSalvarHotel {
        void salvouHotel(Hotel hotel);
    }
}
