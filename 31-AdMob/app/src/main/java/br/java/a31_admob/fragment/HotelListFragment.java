package br.java.a31_admob.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.java.a31_admob.R;
import br.java.a31_admob.adapter.HotelCursorAdapter;
import br.java.a31_admob.modelo.Hotel;
import br.java.a31_admob.repository.HotelRepositorio;
import br.java.a31_admob.service.HotelIntentService;


public class HotelListFragment extends ListFragment
         implements ActionMode.Callback,
                    AdapterView.OnItemLongClickListener,
                    LoaderManager.LoaderCallbacks<Cursor>,
                    SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    ListView mListView;
    ActionMode mActionMode;

    HotelRepositorio mHotelRepositorio;

    CursorAdapter mAdapter;
    String mTextoBusca;

    SwipeRefreshLayout mSwipeLayout;

    BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mSwipeLayout.setRefreshing(false);

            if (!intent.getBooleanExtra(HotelIntentService.EXTRA_SUCESSO, false)) {

                Toast.makeText(getActivity(), R.string.erro_sincronizacao, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        IntentFilter filter = new IntentFilter(HotelIntentService.ACAO_SINCRONIZAR);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mServiceReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mServiceReceiver
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_list_hotel, null);
        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark
        );
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHotelRepositorio = new HotelRepositorio(getActivity());
        mAdapter = new HotelCursorAdapter(getActivity(), null);
        mListView = getListView();
        setListAdapter(mAdapter);
        mListView.setOnItemLongClickListener(this);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mActionMode != null) {

        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mActionMode == null ){

            Activity activity = getActivity();

            if (activity instanceof AoClicarNoHotel) {

                Cursor cursor = (Cursor) l.getItemAtPosition(position);
                Hotel hotel = HotelRepositorio.hotelFromCursor(cursor);

                AoClicarNoHotel listener = (AoClicarNoHotel) activity;
                listener.clicouNoHotel(hotel);
            }
        } else {


        }
    }

    public void buscar(String s) {
        mTextoBusca = TextUtils.isEmpty(s) ? null : s;
        getLoaderManager().restartLoader(0, null, this);
    }

    public void limparBusca() {
        buscar(null);
    }

    public interface AoClicarNoHotel {
        void clicouNoHotel(Hotel hotel);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.string.acao_delete) {

            remover();
            actionMode.finish();

            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

        mActionMode = null;

        for(int i = 0; i < mListView.getCount(); i++) {
            mListView.setItemChecked(i, false);
        }

        mListView.clearChoices();
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);

        limparBusca();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        boolean consumed = (mActionMode == null);

        if (consumed) {
            iniciarModoExclusao();
            mListView.setItemChecked(i, true);
            atualizarItensMarcados(mListView, i);
        }
        return consumed;
    }

    private void iniciarModoExclusao() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mActionMode = activity.startSupportActionMode(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void atualizarItensMarcados(ListView l, int position) {
        l.setItemChecked(position, l.isItemChecked(position));

        atualizarTitulo();
    }

    private int qtdeItensMarcados() {
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        int checkedCount = 0;

        for(int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {

                checkedCount++;
            }
        }

        return checkedCount;
    }

    private void atualizarTitulo() {
        int checkedCount = qtdeItensMarcados();
        String selecionados = getResources().getQuantityString(
                R.plurals.numero_selecionados,
                checkedCount, checkedCount
        );

        mActionMode.setTitle(selecionados);
    }

    private void remover() {

        final List<Hotel> hoteisExcluidos = new ArrayList<Hotel>();
        SparseBooleanArray checked = mListView.getCheckedItemPositions();

        for(int i = checked.size() -1; i >= 0; i--) {

            if (checked.valueAt(i)) {
                int position = checked.keyAt(i);
                Cursor cursor = (Cursor) mListView.getItemAtPosition(position);
                Hotel hotel = mHotelRepositorio.hotelFromCursor(cursor);
                hoteisExcluidos.add(hotel);
                mHotelRepositorio.excluir(hotel);
            }
        }

        Activity activity = getActivity();

        if (activity instanceof AoExcluirHoteis) {
            AoExcluirHoteis aoExcluirHoteis =
                    (AoExcluirHoteis) activity;
            aoExcluirHoteis.exclusapCompleta(hoteisExcluidos);
        }

        Snackbar.make(mListView,
                getString(R.string.mensagem_excluir, hoteisExcluidos.size()),
                Snackbar.LENGTH_LONG)
        .setAction(R.string.desfazer, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Hotel hotel : hoteisExcluidos) {
                    hotel.status = Hotel.Status.ATUALIZAR;
                    mHotelRepositorio.inserirLocal(hotel, getActivity().getContentResolver());
                }

                limparBusca();
            }
        }).show();
    }

    public interface AoExcluirHoteis {
        void exclusapCompleta(List<Hotel> hoteis);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return mHotelRepositorio.buscar(getActivity(), mTextoBusca);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onRefresh() {
        Intent it = new Intent(getActivity(), HotelIntentService.class);
        getActivity().startService(it);
    }
}