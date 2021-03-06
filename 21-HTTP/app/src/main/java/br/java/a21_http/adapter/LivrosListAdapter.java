package br.java.a21_http.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.java.a21_http.R;
import br.java.a21_http.modelo.Livro;



public class LivrosListAdapter extends ArrayAdapter<Livro> {

    public LivrosListAdapter(@NonNull Context context, List<Livro> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Livro livro = getItem(position);
        ViewHolder holder;
        if (convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_livro_list, null);
            holder = new ViewHolder();
            holder.imgCapa = (ImageView) convertView.findViewById(R.id.imgCapa);
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.txtTitulo);
            holder.txtAutores = (TextView) convertView.findViewById(R.id.txtAutores);
            holder.txtPaginas = (TextView) convertView.findViewById(R.id.txtPaginas);
            holder.txtAno = (TextView) convertView.findViewById(R.id.txtAno);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext()).load(livro.capa).into(holder.imgCapa);
        holder.txtTitulo.setText(livro.titulo);
        holder.txtAutores.setText(livro.autor);
        holder.txtAno.setText(String.valueOf(livro.ano));
        holder.txtPaginas.setText(getContext().getString(R.string.n_paginas,livro
        .paginas));

        return convertView;
    }

    static class ViewHolder {
        ImageView imgCapa;
        TextView txtTitulo;
        TextView txtAutores;
        TextView txtPaginas;
        TextView txtAno;
    }
}
