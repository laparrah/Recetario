package laparrah.matechs.recetario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import laparrah.matechs.recetario.adapters.RecetasAdapter;
import laparrah.matechs.recetario.clases.Receta;

public class recetasCategorias extends AppCompatActivity {

    DBHandler conn;
    GridView gridViewRecetasCategoria;
    ArrayList<Receta> listRecetas;
    RecetasAdapter adapter = null;
    int idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetascategorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Recetas por categoría");

        conn = new DBHandler(getApplicationContext());
        gridViewRecetasCategoria = (GridView) findViewById(R.id.gridviewRecetasCategoria);
        listRecetas = new ArrayList<>();

        Intent i = getIntent();
        String id_Categoria = i.getStringExtra("id_Categoria");
        idCategoria = Integer.parseInt(id_Categoria);

        SharedPreferences sharedPreferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = conn.cargarRecetasporCategoria(idCategoria, idUsuario);
        adapter = new RecetasAdapter(getApplicationContext(), listRecetas);
        gridViewRecetasCategoria.setAdapter(adapter);

        gridViewRecetasCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //se guarda un auxiliar del id de la receta que se obtiene desde el adaptador
                // y se guarda en un Extra para usarlo en la consulta de la info de la receta
                Intent intent = new Intent(getApplicationContext(), detalleReceta.class);
                intent.putExtra("id_Receta", adapter.auxidReceta);

                startActivity(intent);
            }
        });
    }
}