package laparrah.matechs.recetario.clases;

/**
 * Created by lauranellyparra on 10/12/17.
 */

public class Usuario {
    String nombreUsuario, correo, contraseña;
    int sesion, idUsuario;

    public Usuario(){}

    public Usuario(int idUsuario, String nombreUsuario, String correo, String contraseña, int sesion){
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.sesion = sesion;
    }

    public String getNombreUsuario() {return nombreUsuario;}

    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario = nombreUsuario;}

    public String getCorreo() {return correo;}

    public void setCorreo(String correo) {this.correo = correo;}

    public String getContraseña() {return contraseña;}

    public void setContraseña(String contraseña) {this.contraseña = contraseña;}

    public int getSesion() {return sesion;}

    public void setSesion(int sesion) {this.sesion = sesion;}

    public int getIdUsuario() {return idUsuario;}

    public void setIdUsuario(int idUsuario) {this.idUsuario = idUsuario;}
}