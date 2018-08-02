package laparrah.matechs.recetario;

import android.content.DialogInterface;
import android.content.Intent;
import com.getbase.floatingactionbutton.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import laparrah.matechs.recetario.clases.Receta;

public class detalleReceta extends AppCompatActivity {
    DBHandler conn;
    int idReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallereceta);

        final ImageView imgDescripcionReceta = (ImageView) findViewById(R.id.imgvDecripcionReceta);
        final TextView txtvNombreReceta = (TextView) findViewById(R.id.txtvNombreReceta);
        final TextView txtvPorciones = (TextView) findViewById(R.id.txtvPorciones);
        final TextView txtvTiempoPreparacion = (TextView) findViewById(R.id.txtvTiempoPreparacion);
        final TextView txtvDificultad = (TextView) findViewById(R.id.txtvDificultad);
        final TextView txtIdReceta = (TextView) findViewById(R.id.txtIdReceta);

        final MultiAutoCompleteTextView txtIngredientes = (MultiAutoCompleteTextView) findViewById(R.id.txtvIngredientes);
        final MultiAutoCompleteTextView txtPreparacion = (MultiAutoCompleteTextView) findViewById(R.id.txtvPreparacion);

        setTitle("Detalle de receta");

        Intent i = getIntent();
        String id_Receta = i.getStringExtra("id_Receta");
        idReceta = Integer.parseInt(id_Receta);

        conn = new DBHandler(this);
        ArrayList<Receta> listReceta = conn.detalleReceta(idReceta);

        if(!listReceta.isEmpty()){
            for (Receta receta : listReceta) {
                txtIdReceta.setText(String.valueOf(receta.getIdReceta()));
                txtvNombreReceta.setText(receta.getNombre_receta());
                imgDescripcionReceta.setImageResource(receta.getIdFotoReceta());
                txtvPorciones.setText(String.valueOf(receta.getPorciones())+" porciones");
                txtvTiempoPreparacion.setText(String.valueOf(receta.getTiempo())+" minutos");
                txtvDificultad.setText("Dificultad: "+receta.getDificultad());
                txtIngredientes.setText(receta.getIngredientes());
                txtPreparacion.setText(receta.getPreparacion());
            }

        } else{ Toast.makeText(getApplicationContext(),"No hay información para mostrar", Toast.LENGTH_SHORT).show();}

        FloatingActionButton fabEditar = (FloatingActionButton) findViewById(R.id.fabEditar);
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), actualizarReceta.class);
                intent.putExtra("id_Receta", String.valueOf(txtIdReceta.getText()));
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fabEliminar = (FloatingActionButton) findViewById(R.id.fabEliminar);
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(detalleReceta.this)
                        .setTitle("Eliminar receta")
                        .setMessage("¿Está seguro que desea eliminar esta receta?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                conn.eliminarReceta(idReceta);
                                Toast.makeText(getApplicationContext(), "La receta se ha eliminado", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }
}