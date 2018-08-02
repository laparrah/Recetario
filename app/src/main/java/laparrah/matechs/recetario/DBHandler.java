package laparrah.matechs.recetario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;

import laparrah.matechs.recetario.clases.Categoria;
import laparrah.matechs.recetario.clases.Receta;
import laparrah.matechs.recetario.clases.Usuario;

/**
 * Created by lauranellyparra on 27/11/17.
 */

public class DBHandler extends SQLiteOpenHelper {
    public SQLiteDatabase datab;
    ContentValues values = new ContentValues();

    //TABLA CATEGORIA
    private static String TABLA_CATEGORIA = "categoria", CAT_ID = "idCategoria", CAT_NOMBRE = "nombre", CAT_FOTO = "idFoto";

    //TABLA USUARIOS
    private static String TABLA_USUARIOS = "usuarios", USR_ID = "idUsuario", USR_NOMBRE = "nombre",
            USR_EMAIL = "correo", USR_PASS = "contrasena", USR_SESION = "sesion";

    //TABLA RECETAS
    private static String TABLA_RECETAS = "recetas", REC_ID = "idReceta", REC_NOMBRE = "nombre",
            REC_PORCION = "porciones", REC_PREPT = "tiempoPreparacion", REC_ING = "ingredientes", REC_PREP = "preparacion",
            REC_DIFICULTAD = "dificultad", REC_FKCAT = "fkCategoria", REC_FREC = "fotoReceta", REC_FKUSR = "fkUsuario";


    public DBHandler(Context context) {
        super(context, "cook_book.db", null, 15);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tabla categorias
        db.execSQL("CREATE TABLE " + TABLA_CATEGORIA + "(" +
                CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAT_FOTO + " INTEGER, " +
                CAT_NOMBRE + " VARCHAR(100)" +
                ");"
        );

        //Tabla usuarios
        db.execSQL("CREATE TABLE "+ TABLA_USUARIOS +"(" +
                USR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USR_NOMBRE + " VARCHAR(200), " +
                USR_EMAIL + " VARCHAR(250), " +
                USR_PASS + " VARCHAR(20), " +
                USR_SESION + " INTEGER(2) " +
                ");"
        );

        //Tabla Recetas
        db.execSQL("CREATE TABLE " + TABLA_RECETAS +"(" +
                REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REC_FKCAT + " INTEGER, " +
                REC_FKUSR + " INTEGER, " +
                REC_NOMBRE + " VARCHAR(250), " +
                REC_FREC + " INTEGER, " +
                REC_PORCION + " INTEGER, " +
                REC_PREPT + " VARCHAR(5), " +
                REC_DIFICULTAD + " VARCHAR(50), " +
                REC_ING + " TEXT, " +
                REC_PREP + " TEXT, " +
                " FOREIGN KEY (" + REC_FKCAT + ") REFERENCES " + TABLA_CATEGORIA + "(" + CAT_ID + ") , "+
                " FOREIGN KEY (" + REC_FKUSR + ") REFERENCES " + TABLA_USUARIOS + "(" + USR_ID + ") );"
        );

        //insercion de categorias
        db.execSQL("INSERT INTO "+TABLA_CATEGORIA+" ("+CAT_NOMBRE+", "+CAT_FOTO+" ) " +
                    "VALUES ('Bebidas', "+R.drawable.bebidas+"), ('Ensaladas', "+R.drawable.ensaladas+"), "+
                    "('Pastas', "+R.drawable.pastas+"), ('Platos fuertes', "+R.drawable.platosfuertes+"), "+
                    "('Sopa', "+R.drawable.sopas+"), ('Guarniciones', "+R.drawable.guarnicion+"), "+
                    "('Postres', "+R.drawable.postres+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_RECETAS);
        onCreate(db);
    }

    //consulta para el login
    public boolean login(String usuario, String contraseña) throws SQLException {
        datab = this.getWritableDatabase();

        Cursor cursor = datab.rawQuery("SELECT * FROM " + TABLA_USUARIOS +
                " WHERE " + USR_EMAIL + "='"+usuario+"' AND " + USR_PASS + "='"+contraseña+"' ;", null);
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        datab.close();
        return false;
    }

    //Registro de nuevos usuarios
    public void registrarUsuario(String nombre, String correo, String contraseña, int sesion){
        datab = this.getWritableDatabase();
        values.put(USR_NOMBRE, nombre);
        values.put(USR_EMAIL, correo);
        values.put(USR_PASS, contraseña);
        values.put(USR_SESION, sesion);

        datab.insert(TABLA_USUARIOS, null, values);
        datab.close();
    }

    //Registro de nuevas recetas
    public void nuevaReceta(String nombreReceta, int categoria, String dificultad, int tiempo, int porcion,
                            String ingredientes, String preparacion, int foto, int usuario){
        datab = this.getWritableDatabase();

        values.put(REC_NOMBRE, nombreReceta);
        values.put(REC_PORCION, porcion);
        values.put(REC_ING, ingredientes);
        values.put(REC_PREP, preparacion);
        values.put(REC_FKCAT, categoria);
        values.put(REC_DIFICULTAD, dificultad);
        values.put(REC_PREPT, tiempo);
        values.put(REC_FREC, foto);
        values.put(REC_FKUSR, usuario);

        datab.insert(TABLA_RECETAS, null, values);
        datab.close();
    }

    //encontrar usuario
    public int encontrarUsuario(String correo){
        datab = this.getReadableDatabase();
        int idUsuario = 0;

        String query = "SELECT "+USR_ID+ " FROM "+TABLA_USUARIOS+" WHERE "+USR_EMAIL+"='"+correo+"';)";
        Cursor cursor = datab.rawQuery(query, null);
        if(cursor.moveToFirst()){
            idUsuario = cursor.getInt(0);
        } else {}
        datab.close();
        return idUsuario;
    }

    //Actualizacion de la receta
    public void actualizarReceta(String idReceta, String nombreReceta, int categoria, String dificultad,
                                 int tiempo, int porcion, String ingredientes, String preparacion, int foto){
        datab = this.getWritableDatabase();
        values.put(REC_NOMBRE, nombreReceta);
        values.put(REC_PORCION, porcion);
        values.put(REC_ING, ingredientes);
        values.put(REC_PREP, preparacion);
        values.put(REC_FKCAT, categoria);
        values.put(REC_DIFICULTAD, dificultad);
        values.put(REC_PREPT, tiempo);
        values.put(REC_FREC, foto);

        datab.update(TABLA_RECETAS, values, REC_ID+"=?", new String[]{idReceta});
        datab.close();
    }

    //Eliminar una receta
    public void eliminarReceta(int idReceta){
        datab = this.getWritableDatabase();

        datab.delete(TABLA_RECETAS, REC_ID+"=?", new String[]{String.valueOf(idReceta)});

        datab.close();
    }

    //consulta para obtener información de la sesión
    public ArrayList<Usuario> getInfoUsuario(String correo){
        datab = this.getReadableDatabase();
        ArrayList<Usuario> users = new ArrayList<>();

        String query = "SELECT * FROM "+TABLA_USUARIOS+" WHERE "+USR_EMAIL+"='"+correo+"';";
        Cursor cursor = datab.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Usuario list_user = new Usuario();
                list_user.setIdUsuario(cursor.getInt(0));
                list_user.setNombreUsuario(cursor.getString(1));
                users.add(list_user);
            } while (cursor.moveToNext());
        }
        datab.close();
        return users;
    }

    //consulta de recetas para el gridview
    public ArrayList<Receta> cargarRecetas(int idUsuario){
        datab = this.getWritableDatabase();
        ArrayList<Receta> recetas = new ArrayList<>();
        String query = "SELECT * FROM "+TABLA_RECETAS+ " WHERE "+REC_FKUSR+"="+idUsuario+";";

        Cursor cursor = datab.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Receta list_rec = new Receta();

                list_rec.setIdReceta(cursor.getInt(0));
                list_rec.setCategoria(cursor.getInt(1));
                list_rec.setUsuario(cursor.getInt(2));
                list_rec.setNombre_receta(cursor.getString(3));
                list_rec.setIdFotoReceta(cursor.getInt(4));
                list_rec.setPorciones(cursor.getString(5));
                list_rec.setTiempo(cursor.getString(6));
                list_rec.setDificultad(cursor.getString(7));
                list_rec.setIngredientes(cursor.getString(8));
                list_rec.setPreparacion(cursor.getString(9));
                recetas.add(list_rec);
            } while(cursor.moveToNext());
        }
        datab.close();
        return recetas;
    }

    //consulta de categorias
    public ArrayList<Categoria> CargarCategorias(){
        datab = this.getWritableDatabase();
        ArrayList<Categoria> categorias = new ArrayList<>();
        //QUERY para obtener las categorias     , "+CAT_FOTO+"
        String query = "SELECT "+CAT_NOMBRE+", "+CAT_FOTO+" FROM "+TABLA_CATEGORIA+" ORDER BY "+CAT_NOMBRE;

        Cursor cursor = datab.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Categoria list_cat = new Categoria();
                list_cat.setNombre_categoria(cursor.getString(0));
                list_cat.setCatgFoto(cursor.getInt(1));
                categorias.add(list_cat);
            } while(cursor.moveToNext());
        }
        datab.close();
        return categorias;
    }

    //consulta si hay recetas registradas
    public boolean hayRecetasRegistradas(int idCategoria, int idUsuario){
        datab = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLA_RECETAS+" WHERE "+REC_FKCAT+"="+idCategoria+ " AND "+REC_FKUSR+"="+idUsuario+"; ";
        Cursor cursor = datab.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        datab.close();
        return false;
    }

    //consulta las recetas que tiene la categoria seleccionada
    public ArrayList<Receta> cargarRecetasporCategoria(int idCategoria, int idUsuario){
        datab = this.getReadableDatabase();
        ArrayList<Receta> recetasCategoria = new ArrayList<>();
        String query = "SELECT * FROM "+TABLA_RECETAS+" WHERE "+REC_FKCAT+"="+idCategoria+ " AND "+REC_FKUSR+"="+idUsuario+"; ";

        Cursor cursor = datab.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                Receta listRecCat = new Receta();

                listRecCat.setIdReceta(cursor.getInt(0));
                listRecCat.setCategoria(cursor.getInt(1));
                listRecCat.setUsuario(cursor.getInt(2));
                listRecCat.setNombre_receta(cursor.getString(3));
                listRecCat.setIdFotoReceta(cursor.getInt(4));
                listRecCat.setPorciones(cursor.getString(5));
                listRecCat.setTiempo(cursor.getString(6));
                listRecCat.setDificultad(cursor.getString(7));
                listRecCat.setIngredientes(cursor.getString(8));
                listRecCat.setPreparacion(cursor.getString(9));

                recetasCategoria.add(listRecCat);
            } while (cursor.moveToNext());
        }
        datab.close();
        return recetasCategoria;
    }

    //Consulta del detalle de la receta seleccionada
    public ArrayList<Receta> detalleReceta(int idReceta){
        datab = this.getReadableDatabase();
        ArrayList<Receta> detalleReceta = new ArrayList<>();

        String query = "SELECT * FROM "+TABLA_RECETAS+" WHERE "+REC_ID+"="+idReceta+";";
        Cursor cursor = datab.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Receta listDetalleReceta = new Receta();

                listDetalleReceta.setIdReceta(cursor.getInt(0));
                listDetalleReceta.setCategoria(cursor.getInt(1));
                listDetalleReceta.setUsuario(cursor.getInt(2));
                listDetalleReceta.setNombre_receta(cursor.getString(3));
                listDetalleReceta.setIdFotoReceta(cursor.getInt(4));
                listDetalleReceta.setPorciones(cursor.getString(5));
                listDetalleReceta.setTiempo(cursor.getString(6));
                listDetalleReceta.setDificultad(cursor.getString(7));
                listDetalleReceta.setIngredientes(cursor.getString(8));
                listDetalleReceta.setPreparacion(cursor.getString(9));
                detalleReceta.add(listDetalleReceta);
            } while (cursor.moveToNext());
        }
        datab.close();
        return detalleReceta;
    }

}