package br.java.a07_adapter.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.java.a07_adapter.R;
import br.java.a07_adapter.modelo.Carro;

public class Carroadapter extends BaseAdapter {

    Context ctx;
    List<Carro> carros;

    public Carroadapter(Context ctx, List<Carro> carros) {
        this.ctx = ctx;
        this.carros = carros;
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int position) {
        return carros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Etapa 1
        Carro carro = carros.get(position);

        // Etapa 2
        ViewHolder holder = null;

        if (convertView == null) {
            Log.d("NGVL", "View Nova => position: " + position);

            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_carro, null);
            holder = new ViewHolder();
            holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
            holder.txtModelo = (TextView) convertView.findViewById(R.id.txtModelo);
            holder.txtAno = (TextView) convertView.findViewById(R.id.txtAno);
            holder.txtCombustivel = (TextView) convertView.findViewById(R.id.txtCombustivel);
            convertView.setTag(holder);

        } else {
            Log.d("NGVL", "View existente => position: " + position);
            holder = (ViewHolder) convertView.getTag();
        }

        // Etapa 3
        Resources res = ctx.getResources();
        TypedArray logos = res.obtainTypedArray(R.array.logos);
        holder.imgLogo.setImageDrawable(logos.getDrawable(carro.fabircante));
        holder.txtModelo.setText(String.valueOf(carro.modelo));
        holder.txtAno.setText(String.valueOf(carro.ano));
        holder.txtCombustivel.setText((carro.gasolina ? "G" : "") + (carro.etanol ? "E" : ""));

        // Etapa 4
        return convertView;
    }

    static  class ViewHolder {
        ImageView imgLogo;
        TextView txtModelo;
        TextView txtAno;
        TextView txtCombustivel;
    }
}
