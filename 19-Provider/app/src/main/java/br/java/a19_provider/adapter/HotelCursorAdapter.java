package br.java.a19_provider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import br.java.a19_provider.R;
import br.java.a19_provider.sql.HotelSQLHelper;

public class HotelCursorAdapter extends CursorAdapter {

    public HotelCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtMessage = (TextView) view.findViewById(R.id.txtNome);
        RatingBar rtbEstrelas = (RatingBar) view.findViewById(R.id.rtbEstrelas);
        rtbEstrelas.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(HotelSQLHelper.COLUNA_ESTRELAS)));
        txtMessage.setText(cursor.getString(cursor.getColumnIndexOrThrow(HotelSQLHelper.COLUNA_NOME)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_hotel, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ListView listView = (ListView) parent;
        int color = listView.isItemChecked(position) ? Color.argb(0xFF, 0x31, 0xB6, 0xE7) : Color.TRANSPARENT;
        v.setBackgroundColor(color);
        return v;
    }
}
