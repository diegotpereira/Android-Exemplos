package br.java.a20_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import br.java.a20_sqlite.modelo.Hotel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelDetalheFragment#novaInstancia(Hotel)} factory method to
 * create an instance of this fragment.
 */
public class HotelDetalheFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_HOTEL = "hotel";
    TextView mTextNome;
    TextView mTextEndereco;
    RatingBar mRatingEstrelas;
    Hotel mHotel;

    ShareActionProvider mShareActionProvider;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelDetalheFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hotel Parameter 1.
     * @return A new instance of fragment HotelDetalheFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelDetalheFragment novaInstancia(Hotel hotel) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_HOTEL, hotel);
        HotelDetalheFragment fragment = new HotelDetalheFragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHotel = (Hotel) getArguments().getSerializable(EXTRA_HOTEL);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(
                R.layout.fragment_detalhe_hotel, container, false);
        mTextNome = (TextView)
                layout.findViewById(R.id.txtNome);
        mTextEndereco = (TextView)
                layout.findViewById(R.id.txtEndereco);
        mRatingEstrelas = (RatingBar)
                layout.findViewById(R.id.rtbEstrelas);

        if (mHotel != null) {
            mTextNome.setText(mHotel.nome);
            mTextEndereco.setText(mHotel.endereco);
            mRatingEstrelas.setTag(mHotel.estrelas);
        }
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_hotel_detalhe, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        String texto = getString(R.string.texto_compartilhar,
                mHotel.nome, mHotel.estrelas);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT, texto);
        mShareActionProvider.setShareIntent(it);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.acao_editar) {
            Activity activity = getActivity();
            if (activity instanceof AoEditarHotel) {
                AoEditarHotel aoEditarHotel = (AoEditarHotel) activity;
                aoEditarHotel.aoEditarhotel(mHotel);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Hotel getHotel() {
        return mHotel;
    }

    public  interface AoEditarHotel {
        void aoEditarhotel(Hotel hotel);
    }
}