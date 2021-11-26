package br.java.a08_autocomplete.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.java.a08_autocomplete.util.Util;

public class MeuAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> listaCompleta;
    private List<String> resultados;
    private Filter meuFiltro;

    public MeuAutoCompleteAdapter(@NonNull Context ctx, int layout, List<String> textos) {
        super(ctx, layout, textos);
        this.listaCompleta = textos;
        this.resultados = listaCompleta;
        this.meuFiltro = new MeuFiltro();
    }

    private class MeuFiltro extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> temp = new ArrayList<String>();

            if (constraint != null) {
                String term = Util.removeAcentos(constraint.toString().trim().toLowerCase());
                String placeStr;

                for(String p : listaCompleta) {
                    placeStr = Util.removeAcentos(p.toLowerCase());

                    if (placeStr.indexOf(term) > -1) {
                        temp.add(p);
                    }
                }
            }
            filterResults.values = temp;
            filterResults.count = temp.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {

            resultados = (ArrayList<String>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
