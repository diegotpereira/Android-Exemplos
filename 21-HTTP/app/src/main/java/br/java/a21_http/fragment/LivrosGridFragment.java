package br.java.a21_http.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.java.a21_http.LivroHttp;
import br.java.a21_http.R;
import br.java.a21_http.VolleySingleton;
import br.java.a21_http.adapter.LivrosGridAdapter;
import br.java.a21_http.modelo.Livro;

public class LivrosGridFragment extends InternetFragment
           implements Response.Listener<JSONObject>,
                      Response.ErrorListener {

    List<Livro> mLivros;
    GridView mGridView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ArrayAdapter<Livro> mAdapter;
    boolean mIsRunning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_livros_grid, null);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.progressBar);
        mTextMensagem = (TextView) layout.findViewById(android.R.id.empty);
        mGridView = (GridView) layout.findViewById(R.id.gridview);
        mGridView.setEmptyView(mTextMensagem);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mLivros == null) {
            mLivros = new ArrayList<Livro>();
        }

        mAdapter = new LivrosGridAdapter(getActivity(), mLivros);
        mGridView.setAdapter(mAdapter);

        if (!mIsRunning) {
            if (LivroHttp.temConexao(getActivity())) {
                iniciarDownload();

            } else {
                mTextMensagem.setText("Sem conexão");
            }
        } else {
            exibirProgress(true);
        }
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Baixando informações dos livros...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    @Override
    public void iniciarDownload() {
        mIsRunning = true;
        exibirProgress(true);

        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        JsonObjectRequest request =
                new JsonObjectRequest(
                        // Requisição via HTTP_GET
                        Request.Method.GET,
                        // url da requisição
                        LivroHttp.LIVROS_URL_JSON,
                        // JSONObject a ser enviado via POST
                        null,
                        // Response.Listener
                        this,
                        // Response.ErrorListener
                        this);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mIsRunning = false;
        exibirProgress(false);
        mTextMensagem.setText("Falha ao obter livros");
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        mIsRunning = false;
        exibirProgress(false);

        try {
            List<Livro> livros = LivroHttp.lerJsonLivros(jsonObject);
            mLivros.clear();
            mLivros.addAll(livros);
            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
