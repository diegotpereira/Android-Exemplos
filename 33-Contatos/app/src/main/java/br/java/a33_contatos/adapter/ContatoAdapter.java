package br.java.a33_contatos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.squareup.picasso.Picasso;

import br.java.a33_contatos.R;

public class ContatoAdapter extends CursorAdapter {

    int[] indices;

    public ContatoAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        indices = new int[] {
          cursor.getColumnIndex(ContactsContract.Contacts._ID),
          cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY),
          cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        };
        return LayoutInflater.from(context).inflate(R.layout.item_contato, null);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        QuickContactBadge qcbBadge = (QuickContactBadge)
                view.findViewById(R.id.qcbFoto);

        @SuppressLint("RestrictedApi") Uri uriContato = ContactsContract.Contacts.getLookupUri(
                mCursor.getLong(indices[0]),
                mCursor.getString(indices[1]));
        txtNome.setText(cursor.getString(indices[2]));
        qcbBadge.assignContactUri(uriContato);
        Picasso.get()
                .load(uriContato)
                .placeholder(R.mipmap.ic_launcher)
                .into(qcbBadge);
    }
}
