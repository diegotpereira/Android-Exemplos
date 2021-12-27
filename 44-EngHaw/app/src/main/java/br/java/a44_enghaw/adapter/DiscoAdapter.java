package br.java.a44_enghaw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.java.a44_enghaw.http.DiscoHttp;
import br.java.a44_enghaw.R;
import br.java.a44_enghaw.modelo.Disco;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoAdapter extends RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder>{

    private Context mContext;
    private Disco[] mDiscos;
    private AoClicarNoDiscoListener mListener;

    public DiscoAdapter(Context ctx, Disco[]discos) {
        mContext = ctx;
        mDiscos = discos;
    }

    public void setAoClicarNoDiscoListener(AoClicarNoDiscoListener l) {
        mListener = l;
    }

    @Override
    public int getItemCount() {
        return mDiscos != null ? mDiscos.length : 0;
    }

    @NonNull
    @Override
    public DiscoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_disco, parent, false);
        DiscoViewHolder vh = new DiscoViewHolder(v);
        v.setTag(vh);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    DiscoViewHolder vh = (DiscoViewHolder) view.getTag();
                    int position = vh.getAdapterPosition();
                    mListener.aoClicarNoDisco(view, position, mDiscos[position]);
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoViewHolder holder, int position) {
        Disco disco = mDiscos[position];
        Picasso.with(mContext).load(DiscoHttp.BASE_URL + disco.capa).into(holder.imgCapa);
        holder.txtTitulo.setText(disco.titulo);
        holder.txtAno.setText(String.valueOf(disco.ano));
    }

    public interface AoClicarNoDiscoListener {
        void aoClicarNoDisco(View v, int position, Disco disco);
    }

    public static class DiscoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgCapa)
        public ImageView imgCapa;
        @Bind(R.id.txtTitulo)
        public TextView txtTitulo;
        @Bind(R.id.txtAno)
        public TextView txtAno;

        public DiscoViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this, parent);
            ViewCompat.setTransitionName(imgCapa, "capa");
            ViewCompat.setTransitionName(txtTitulo, "titulo");
            ViewCompat.setTransitionName(txtAno, "ano");
        }
    }
}
