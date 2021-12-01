package br.java.a19_provider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import br.java.a19_provider.modelo.Hotel;


public class HotelDetalheFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_HOTEL = "hotel";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    TextView mTextNome;
    TextView mTextEndereco;
    RatingBar mRatingEstrelas;
    Hotel mHotel;

    ShareActionProvider mShareActionProvider;

    public HotelDetalheFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HotelDetalheFragment novaInstancia(Hotel hotel) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_HOTEL, hotel)
        ;
        HotelDetalheFragment fragment = new HotelDetalheFragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotel = (Hotel)
                getArguments().getSerializable(EXTRA_HOTEL);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(
                R.layout.fragment_detalhe_hotel, container, false);

        mTextNome = (TextView) layout.findViewById(R.id.txtNome);
        mTextEndereco = (TextView)
                layout.findViewById(R.id.txtEndereco);
        mRatingEstrelas = (RatingBar)
                layout.findViewById(R.id.rtbEstrelas);

        if (mHotel != null) {
            mTextNome.setText(mHotel.nome);
            mTextEndereco.setText(mHotel.endereco);
            mRatingEstrelas.setRating(mHotel.estrelas);
        }
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public Hotel getHotel()  {
        return mHotel;
    }

    public  interface AoEditarHotel {
        void aoEditarhotel(Hotel hotel);
    }
}