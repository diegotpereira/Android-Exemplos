package br.java.a44_enghaw.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import br.java.a44_enghaw.DetalheActivity;
import br.java.a44_enghaw.DiscoApp;
import br.java.a44_enghaw.DiscoEvento;
import br.java.a44_enghaw.R;
import br.java.a44_enghaw.adapter.DiscoAdapter;
import br.java.a44_enghaw.db.DiscoDb;
import br.java.a44_enghaw.modelo.Disco;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListaDiscosFavoritosFragment extends Fragment
        implements DiscoAdapter.AoClicarNoDiscoListener{

    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    DiscoDb mDiscoDb;
    List<Disco> mDiscos;
    Bus mBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mBus = ((DiscoApp)getActivity().getApplication()).getBus();
        mBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mBus = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_discos, container, false);
        ButterKnife.bind(this, v);

        mSwipe.setEnabled(false);
        mRecyclerView.setTag("fav");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDiscoDb = new DiscoDb(getActivity());
        if (mDiscos == null) {
            mDiscos = mDiscoDb.getDiscos();
        }

        atualizarLista();
    }

    private void atualizarLista() {
        Disco[]array = new Disco[mDiscos.size()];
        mDiscos.toArray(array);
        DiscoAdapter adapter = new DiscoAdapter(getActivity(), array);
        adapter.setAoClicarNoDiscoListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void aoClicarNoDisco(View v, int position, Disco disco) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                Pair.create(v.findViewById(R.id.imgCapa), "capa"),
                Pair.create(v.findViewById(R.id.txtTitulo), "titulo"),
                Pair.create(v.findViewById(R.id.txtAno), "ano")
        );
        Intent it = new Intent(getActivity(), DetalheActivity.class);
        it.putExtra(DetalheActivity.EXTRA_DISCO, disco);
        ActivityCompat.startActivity(getActivity(), it, options.toBundle());
    }
    @Subscribe
    public void atualizarLista(DiscoEvento event) {
        mDiscos = mDiscoDb.getDiscos();
        atualizarLista();
    }

}
