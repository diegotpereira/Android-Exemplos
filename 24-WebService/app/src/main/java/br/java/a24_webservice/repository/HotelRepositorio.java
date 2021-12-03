package br.java.a24_webservice.repository;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.loader.content.CursorLoader;

import java.util.List;

import br.java.a24_webservice.modelo.Hotel;
import br.java.a24_webservice.provider.HotelProvider;
import br.java.a24_webservice.sql.HotelSQLHelper;

public class HotelRepositorio {

    private Context ctx;

    public HotelRepositorio(Context ctx) {
        this.ctx = ctx;
    }

    private long inserir(Hotel hotel) {

        hotel.status = Hotel.Status.INSERIR;
        long id = inserirLocal(hotel, ctx.getContentResolver());

        return id;
    }

    private int atualizar(Hotel hotel) {
        hotel.status = Hotel.Status.ATUALIZAR;

        int linhasAfetadas = atualizarLocal(hotel, ctx.getContentResolver());

        return linhasAfetadas;
    }

    public void salvar(Hotel hotel) {
        if (hotel.id == 0) {
            inserir(hotel);

        } else {
            atualizar(hotel);
        }
    }

//    public int excluir(Hotel hotel) {
//
//    }
//
//    public CursorLoader buscar(Context ctx, String s) {
//
//    }
//
    private ContentValues getValues(Hotel hotel) {
        ContentValues cv = new ContentValues();
        cv.put(HotelSQLHelper.COLUNA_NOME, hotel.nome);
        cv.put(HotelSQLHelper.COLUNA_ENDERECO, hotel.endereco);
        cv.put(HotelSQLHelper.COLUNA_ESTRELAS, hotel.estrelas);
        cv.put(HotelSQLHelper.COLUNA_STATUS, hotel.status.ordinal());

        if (hotel.idServidor != 0) {
            cv.put(HotelSQLHelper.COLUNA_ID_SERVIDOR, hotel.idServidor);
        }

        return cv;
    }
//
//    public static Hotel hotelFromCursor(Cursor cursor) {
//
//    }
//
    public long inserirLocal(Hotel hotel, ContentResolver cr) {
        Uri uri = cr.insert(
                HotelProvider.CONTENT_URI,
                getValues(hotel));
        long id = Long.parseLong(uri.getLastPathSegment());

        if (id != -1) {
            hotel.id = id;
        }

        return id;
    }
//
    public int atualizarLocal(Hotel hotel, ContentResolver cr) {

        Uri uri = Uri.withAppendedPath(
                HotelProvider.CONTENT_URI, String.valueOf(hotel.id));

        int linhasAfetadas = cr.update(
                uri, getValues(hotel), null, null);

        return linhasAfetadas;

    }

//    public int excluirLocal(List<Long> serverIds, ContentResolver cr) {
//
//    }
}
