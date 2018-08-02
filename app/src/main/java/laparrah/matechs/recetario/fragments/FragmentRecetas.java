package laparrah.matechs.recetario.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import laparrah.matechs.recetario.DBHandler;
import laparrah.matechs.recetario.R;
import laparrah.matechs.recetario.adapters.RecetasAdapter;
import laparrah.matechs.recetario.clases.Receta;
import laparrah.matechs.recetario.detalleReceta;

public class FragmentRecetas extends Fragment {

    public FragmentRecetas() {}

    GridView gridviewRecetas;
    ArrayList<Receta> listRecetas;
    RecetasAdapter adapter = null;
    int idUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recetas, container, false);

        DBHandler conn = new DBHandler(getActivity());

        gridviewRecetas = (GridView) view.findViewById(R.id.gridviewRecetas);
        listRecetas = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = conn.cargarRecetas(idUsuario);

        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);

        gridviewRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //se guarda un auxiliar del id de la receta que se obtiene desde el adaptador
                // y se guarda en un Extra para usarlo en la consulta de la info de la receta
                Intent intentMasInfoReceta = new Intent(getActivity(), detalleReceta.class);
                intentMasInfoReceta.putExtra("id_Receta", adapter.auxidReceta);

                startActivity(intentMasInfoReceta);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        //para recargar la lista de recetas una vez que el foco haya regresado al fragment
        DBHandler conn = new DBHandler(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = new ArrayList<>();
        listRecetas = conn.cargarRecetas(idUsuario);
        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);
        super.onResume();
    }

    public void onDestroyView(){
        //para recargar la lista de recetas una vez que el foco haya regresado al fragment
        DBHandler conn = new DBHandler(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = new ArrayList<>();
        listRecetas = conn.cargarRecetas(idUsuario);
        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);

        super.onDestroyView();
    }
}