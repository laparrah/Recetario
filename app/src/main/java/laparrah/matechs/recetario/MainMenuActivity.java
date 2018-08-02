package laparrah.matechs.recetario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import laparrah.matechs.recetario.fragments.FragmentCategorias;
import laparrah.matechs.recetario.fragments.FragmentRecetas;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("RecetApp");
        //Pager para las vistas de inicio: recetas recientes y categorias
        viewPager = (ViewPager) findViewById(R.id.pager);       //esta en app_bar_mainmenu.xml.xml
        setupViewPager(viewPager);

        //tabs que mostrarán los títulos de las opciones
        tabLayout = (TabLayout) findViewById(R.id.tabs);        //esta en app_bar_mainmenumenu.xml
        tabLayout.setupWithViewPager(viewPager);

        //menu de navegación    esta en menu_main
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //método que realiza la configuracion del viewPager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentRecetas(), "Recetas");           //Fragment inicio (recetas recientes)
        adapter.addFragment(new FragmentCategorias(), "Categorías");      //Fragment categorias (categorias)
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> FragmentContent = new ArrayList<>();
        private final List<String> FragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentContent.get(position);
        }

        @Override
        public int getCount() {
            return FragmentContent.size();
        }

        //muestra el fragment que se recibe en la configuración del viewPager
        public void addFragment(Fragment fragment, String title) {
            FragmentContent.add(fragment);
            FragmentTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitle.get(position);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_nuevaReceta){
            DBHandler conn = new DBHandler(getApplicationContext());

            SharedPreferences sharedPreferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
            correo = sharedPreferences.getString("correo_Usuario", "");
            int idUsuario = conn.encontrarUsuario(correo);

            //Toast.makeText(getApplicationContext(), String.valueOf(idUsuario), Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorpre = preferences.edit();
            editorpre.putInt("idUsuario", idUsuario);
            editorpre.commit();

            Intent intent = new Intent(getApplicationContext(), nuevaReceta.class);
            startActivity(intent);

        } else if (id == R.id.nav_cerrarSesion) {
            new AlertDialog.Builder(MainMenuActivity.this)
                    .setTitle("Sesión")
                    .setMessage("¿Está seguro que desea cerrar sesión?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //guarda el estado de inicio de sesion automatico a false
                            SharedPreferences pref = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("flag_sesion", false);
                            editor.commit();

                            //lanza el activity del login y despues de terminar el activity
                           // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //startActivity(intent);
                            finish();

                        }
                    })
                    //cierra el alertdialog
                    .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}