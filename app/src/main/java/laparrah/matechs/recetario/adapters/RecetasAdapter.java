package laparrah.matechs.recetario.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import laparrah.matechs.recetario.R;
import laparrah.matechs.recetario.clases.Receta;

/**
 * Created by lauranellyparra on 25/11/17.
 */

public class RecetasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Receta> listRecetas;
    public String auxidReceta;

    public RecetasAdapter(Context context, ArrayList<Receta> listRecetas){
        this.context = context;
        this.listRecetas = listRecetas;
    }

    @Override
    public int getCount() {
        return listRecetas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listRecetas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        Receta item = (Receta)getItem(posicion);
        view = LayoutInflater.from(context).inflate(R.layout.recetas_items, null);
        ImageView recFoto = (ImageView)view.findViewById(R.id.imgvRecetas);
        TextView nombreReceta = (TextView)view.findViewById(R.id.txtNombreRecetaItem);
        TextView idReceta = (TextView) view.findViewById(R.id.txtIdRecetaItem);

        idReceta.setText(String.valueOf(item.getIdReceta()));
        recFoto.setImageResource(item.getIdFotoReceta());
        nombreReceta.setText(item.getNombre_receta());

        auxidReceta = String.valueOf(item.getIdReceta());
        return view;
    }
}