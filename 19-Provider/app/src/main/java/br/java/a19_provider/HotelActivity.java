package br.java.a19_provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.java.a19_provider.modelo.Hotel;
import br.java.a19_provider.repository.HotelRepositorio;

public class HotelActivity extends AppCompatActivity
        implements
        HotelListFragment.AoClicarNoHotel,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        HotelDialogFragment.AoSalvarHotel,
        HotelDetalheFragment.AoEditarHotel,
        HotelListFragment.AoExcluirHoteis {

    public static final int REQUEST_EDITAR_HOTEL = 0;
    private long mIdSelecionado;

    private FragmentManager mFragmentManager;
    private HotelListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        mFragmentManager = getSupportFragmentManager();
        mListFragment = (HotelListFragment)
                mFragmentManager.findFragmentById(R.id.fragmentLista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDITAR_HOTEL && resultCode == RESULT_OK) {
            mListFragment.limparBusca();
        }
    }

    @Override
    public void clicouNoHotel(Hotel hotel) {
        mIdSelecionado = hotel.id;
        if (isTablet()) {
            HotelDetalheFragment fragment =
                    HotelDetalheFragment.novaInstancia(hotel);
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.detalhe, fragment,
                    HotelDetalheFragment.TAG_DETALHE);
            ft.commit();

        } else {
            Intent it = new Intent(this, HotelDetalheActivity.class);
            it.putExtra(HotelDetalheActivity.EXTRA_HOTEL, hotel);
            startActivityForResult(it, REQUEST_EDITAR_HOTEL);
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }

    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotel, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_busca));
        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                SobreDialogFragment dialogFragment = new SobreDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "sobre");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mListFragment.buscar(s);
        return false;
    }

    // para expandir a view
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    // para voltar ao normal
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        mListFragment.limparBusca();
        return true;
    }
    public static class SobreDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_NEGATIVE) {
                        Intent it = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/diegotpereira"));
                        startActivity(it);
                    }
                }
            };
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.sobre_titulo)
                    .setMessage(R.string.sobre_mensagem)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(R.string.sobre_botao_site, listener)
                    .create();

            return dialog;
        }
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

    @Override
    public void aoEditarhotel(Hotel hotel) {
        HotelDialogFragment editNameDialog = HotelDialogFragment.newInstance(hotel);
        editNameDialog.abrir(getSupportFragmentManager());
    }

    @Override
    public void exclusaoCompleta(List<Hotel> excluidos) {
        HotelDetalheFragment f = (HotelDetalheFragment)
                mFragmentManager.findFragmentByTag(HotelDetalheFragment.TAG_DETALHE);

        if (f != null) {
            boolean encontrou = false;

            for(Hotel h : excluidos) {
                if (h.id == mIdSelecionado) {
                    encontrou = true;
                    break;
                }
            }

            if (encontrou) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.remove(f);
                ft.commit();
            }
        }
    }
}