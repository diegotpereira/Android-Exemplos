package br.java.a24_webservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import br.java.a24_webservice.fragment.HotelDialogFragment;
import br.java.a24_webservice.fragment.HotelListFragment;
import br.java.a24_webservice.modelo.Hotel;
import br.java.a24_webservice.repository.HotelRepositorio;

public class HotelActivity extends AppCompatActivity
         implements

        MenuItemCompat.OnActionExpandListener,
        HotelDialogFragment.AoSalvarHotel{

    private HotelListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
    }

    public void clicouNoHotel(Hotel hotel) {

    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public void salvouHotel(Hotel hotel) {
        HotelRepositorio repositorio = new HotelRepositorio(this);
        repositorio.salvar(hotel);
        mListFragment.limparBusca();

        if (isTablet()) {
            clicouNoHotel(hotel);
        }
    }

    public void adicionarHotelClick(View v) {
        HotelDialogFragment hotelDialogFragment =
                HotelDialogFragment.newInstance(null);
        hotelDialogFragment.abrir(getSupportFragmentManager());
    }
}