package br.java.a19_provider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.java.a19_provider.modelo.Hotel;

public class HotelDetalheActivity extends AppCompatActivity
             implements  HotelDetalheFragment.AoEditarHotel,
             HotelDialogFragment.AoSalvarHotel{

    public static final String EXTRA_HOTEL = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detalhe);
    }

    @Override
    public void aoEditarhotel(Hotel hotel) {

    }

    @Override
    public void salvouHotel(Hotel hotel) {

    }
}