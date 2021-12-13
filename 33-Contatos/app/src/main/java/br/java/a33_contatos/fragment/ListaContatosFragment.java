package br.java.a33_contatos.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import br.java.a33_contatos.adapter.ContatoAdapter;

public class ListaContatosFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private final static String[] COLUNAS = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    private CursorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAdapter == null) {
            mAdapter = new ContatoAdapter(getActivity(), null);
            setListAdapter(mAdapter);
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        long idContato = cursor.getLong(
                cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

        String lookupKey =  cursor.getString(
                cursor.getColumnIndexOrThrow(ContactsContract.Contacts.LOOKUP_KEY));

        Uri uriContato = ContactsContract.Contacts.getLookupUri(idContato, lookupKey);
        Intent it = new Intent(Intent.ACTION_VIEW, uriContato);

        startActivity(it);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                COLUNAS,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null,
                ContactsContract.Contacts.DISPLAY_NAME
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
