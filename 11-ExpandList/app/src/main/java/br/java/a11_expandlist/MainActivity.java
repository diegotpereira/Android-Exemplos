package br.java.a11_expandlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.java.a11_expandlist.adapter.MeuExpandableAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);

        List<String> listRs = new ArrayList<String>();
        listRs.add("Porto Alegre");
        listRs.add("Canoas");

        List<String> listSp = new ArrayList<String>();
        listSp.add("São Paulo");
        listSp.add("Guarulhos");

        Map<String, List<String>> dados = new HashMap<String, List<String>>();
        dados.put("RS", listRs);
        dados.put("SP", listSp);
        listView.setAdapter(new MeuExpandableAdapter(dados));

    }
}