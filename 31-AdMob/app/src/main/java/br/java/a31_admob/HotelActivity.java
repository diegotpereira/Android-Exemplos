package br.java.a31_admob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.java.a31_admob.modelo.Hotel;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
    }


    public void clicouNoHotel(Hotel hotel) {}

    public void adicionarHotelClick(View v){}
}