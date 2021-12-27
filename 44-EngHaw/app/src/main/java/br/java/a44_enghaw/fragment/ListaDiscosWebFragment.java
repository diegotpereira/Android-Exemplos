package br.java.a44_enghaw.fragment;

import android.os.AsyncTask;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import br.java.a44_enghaw.R;
import br.java.a44_enghaw.adapter.DiscoAdapter;
import br.java.a44_enghaw.modelo.Disco;
import butterknife.Bind;

public class ListaDiscosWebFragment extends Fragment
        implements DiscoAdapter.AoClicarNoDiscoListener{

    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    Disco[] mDiscos;
    DiscosDownloadTask mTask;

    @Override
    public void aoClicarNoDisco(View v, int position, Disco disco) {

    }

    class DiscosDownloadTask extends AsyncTask<Void, Void, Disco[]> {

        @Override
        protected Disco[] doInBackground(Void... voids) {
            return new Disco[0];
        }
    }
}
