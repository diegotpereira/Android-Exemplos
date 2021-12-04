package br.java.a29_googleplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.java.a29_googleplus.R;
import br.java.a29_googleplus.modelo.Pessoa;

public class PessoaAdapter extends ArrayAdapter<Pessoa> {

    public PessoaAdapter(@NonNull Context context, List<Pessoa> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.pessoa_item_list, null);
        }

        Pessoa person = getItem(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.imagemView);
        TextView txt = (TextView) convertView.findViewById(R.id.textoView);
        txt.setText(person.nome);

        Picasso.with(getContext()).load(person.urlFoto).into(img);

        return convertView;
    }
}
