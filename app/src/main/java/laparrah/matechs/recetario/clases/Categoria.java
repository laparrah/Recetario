package laparrah.matechs.recetario.clases;

/**
 * Created by lauranellyparra on 29/11/17.
 */

public class Categoria {
    int idCategoria , catgFoto;
    String nombre_categoria;

    public Categoria() {}

    public Categoria(int idCategoria, int catgFoto, String nombre_categoria){
        this.idCategoria = idCategoria;
        this.catgFoto = catgFoto;
        this.nombre_categoria = nombre_categoria;
    }

    public int getIdCategoria() { return idCategoria; }

    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getCatgFoto() { return catgFoto; }

    public void setCatgFoto(int catgFoto) { this.catgFoto = catgFoto; }

    public String getNombre_categoria() { return nombre_categoria; }

    public void setNombre_categoria(String nombre_categoria) { this.nombre_categoria = nombre_categoria; }
}