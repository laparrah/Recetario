package laparrah.matechs.recetario.clases;

/**
 * Created by lauranellyparra on 27/11/17.
 */

public class Receta {
    String nombre_receta, preparacion, dificultad, porciones, tiempo;
    String ingredientes;
    int idReceta,  idFotoReceta, categoria, usuario;

    public Receta(){}

    public Receta(int idReceta, String nombre_receta, int categoria, int usuario, String dificultad, String porciones,
                  String ingredientes, String preparacion, int idFotoReceta, String tiempo){
        this.nombre_receta = nombre_receta;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.porciones = porciones;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.idFotoReceta = idFotoReceta;
        this.idReceta = idReceta;
        this.tiempo = tiempo;
        this.usuario = usuario;
    }

    public int getUsuario() {return usuario;}

    public void setUsuario(int usuario) {this.usuario = usuario;}

    public int getIdReceta() { return idReceta; }

    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }

    public String getNombre_receta() { return nombre_receta; }

    public void setNombre_receta(String nombre_receta) { this.nombre_receta = nombre_receta; }

    public String getPreparacion() { return preparacion; }

    public void setPreparacion(String preparacion) { this.preparacion = preparacion; }

    public int getCategoria() { return categoria; }

    public void setCategoria(int categoria) { this.categoria = categoria; }

    public String getDificultad() { return dificultad; }

    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public int getIdFotoReceta() { return idFotoReceta; }

    public void setIdFotoReceta(int idFotoReceta) { this.idFotoReceta = idFotoReceta; }

    public String getIngredientes() { return ingredientes; }

    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }

    public String getPorciones() { return porciones; }

    public void setPorciones(String porciones) { this.porciones = porciones; }

    public String getTiempo() {return tiempo;}

    public void setTiempo(String tiempo) {this.tiempo = tiempo;}
}