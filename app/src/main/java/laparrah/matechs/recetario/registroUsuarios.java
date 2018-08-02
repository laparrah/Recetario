package laparrah.matechs.recetario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registroUsuarios extends AppCompatActivity {
    DBHandler conn;
    EditText txtUser, txtEmail, txtPass1, txtPass2;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevousuario);
        this.setTitle("Nuevo Usuario");

        txtUser = (EditText) findViewById(R.id.txtUsuario);
        txtEmail = (EditText) findViewById(R.id.txtemail);
        txtPass1 = (EditText) findViewById(R.id.txtpassw1);
        txtPass2 = (EditText) findViewById(R.id.txtpassw2);
        btnRegistrar = (Button)findViewById(R.id.btnGuardarUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comprueba que no existan campos vacios
                if(txtUser.getText().toString().equals("") | txtEmail.getText().toString().equals("") |
                        txtPass1.getText().toString().equals("") | txtPass2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Existen campos vacíos", Toast.LENGTH_SHORT).show();

                }
                //comprueba que las contraseñas coincidan
                 else if(!(txtPass1.getText().toString().equals(txtPass2.getText().toString()))){
                    Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT).show();
                    txtPass1.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                    txtPass2.setTextColor(getApplicationContext().getResources().getColor(R.color.red));

                } else{
                    //realiza el registro en la BD
                    conn = new DBHandler(getApplicationContext());
                    String nombre = txtUser.getText().toString(),
                            correo = txtEmail.getText().toString(),
                            pass = txtPass1.getText().toString();

                    conn.registrarUsuario(nombre, correo, pass,1);

                    //pone en true la sesion automática
                    SharedPreferences sharedPreferences = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("flag_sesion", true);
                    editor.commit();

                    SharedPreferences preferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorpre = preferences.edit();
                    editorpre.putString("correo_Usuario", correo);
                    editorpre.commit();

                    Toast.makeText(registroUsuarios.this, "¡El registro ha finalizado correctamente!", Toast.LENGTH_SHORT).show();

                    //lanza el activity donde se encuentra el menu NavigationDrawer
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}