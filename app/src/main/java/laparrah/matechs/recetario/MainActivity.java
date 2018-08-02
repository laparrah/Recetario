package laparrah.matechs.recetario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity{

    public String usuario, contrasenia;
    DBHandler conn;
    EditText correo, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new DBHandler(this);

        TextView registrar_usuario = (TextView) findViewById(R.id.clic);

        //Verificar el estado del flag de sesion para decidir la acción que el
        //activity realizará (no guardar la sesion o guardarla)
        SharedPreferences sharedPreferences = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
        boolean sesionIniciada = sharedPreferences.getBoolean("flag_sesion", false);

        if(sesionIniciada == true){
            Intent i = new Intent(this, MainMenuActivity.class);
            startActivity(i);
            finish();
        }

        Button btnLogin = (Button) findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = (EditText) findViewById(R.id.txtCorreo);
                contraseña = (EditText) findViewById(R.id.txtPass);

                usuario = correo.getText().toString();
                contrasenia = contraseña.getText().toString();

                if(usuario.equals("") || contrasenia.equals("")){
                    Toast.makeText(getApplicationContext(), "Aún no hay datos para iniciar sesión", Toast.LENGTH_SHORT).show();
                    correo.setFocusable(true);
                    contraseña.setFocusable(true);
                } else{
                    boolean loginn = false;

                    try{
                        loginn = conn.login(usuario, contrasenia);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                    if(loginn == true){
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        //intent.putExtra("correo_Usuario", usuario);

                        SharedPreferences pref = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("flag_sesion", true);
                        editor.commit();

                        SharedPreferences preferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorpre = preferences.edit();
                        editorpre.putString("correo_Usuario", usuario);
                        editorpre.commit();

                        Toast.makeText(getApplicationContext(), "¡Bienvenid@ a RecetApp!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    } else if(loginn == false){
                        Toast.makeText(getApplicationContext(), "El usuario o contraseña están incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), registroUsuarios.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}