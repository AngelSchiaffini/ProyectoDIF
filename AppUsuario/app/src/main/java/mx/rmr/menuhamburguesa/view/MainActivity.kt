package mx.rmr.menuhamburguesa.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.ui.NavigationUI
import mx.rmr.menuhamburguesa.R
import mx.rmr.menuhamburguesa.databinding.ActivityMainBinding
import mx.rmr.menuhamburguesa.model.Usuario
import mx.rmr.menuhamburguesa.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity(), HomeFragment.DataChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.consultarInfo, R.id.calificarComedor,
                R.id.mapasFragment, R.id.menuFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cerrarSesion -> {
                    println("Se salio we")
                    envioCalificacionExitoso()
                    true
                }
                else -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    // Delega el manejo de los demás elementos del menú a la implementación predeterminada
                    return@setNavigationItemSelectedListener NavigationUI.
                    onNavDestinationSelected(menuItem, navController) ||
                            super.onOptionsItemSelected(menuItem)
                }
            }
        }
    }

    private fun envioCalificacionExitoso() {
        val alerta = AlertDialog.Builder(this)
            .setTitle("AVISO")
            .setMessage("Seguro que quieres cerrar sesion?")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                restartApp()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
        alerta.show()
    }


    private fun restartApp() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }


//
//        // Encuentra y configura las vistas de nombre y correo en el NavigationView
//        val txtNombre = navView.getHeaderView(0).findViewById<TextView>(R.id.tvIdNav)
//        val txtCorreo = navView.getHeaderView(0).findViewById<TextView>(R.id.tvCorreoNav)
//
//        // Establece los datos en las vistas
//        txtNombre.text = "Karla Cruz" // Reemplaza con el nombre real del usuario
//        txtCorreo.text = "KarlaCruz@gmail.com" // Reemplaza con el correo real del usuario




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDataChanged(usuario: Usuario) {
        val navView: NavigationView = binding.navView
        val navHeader = navView.getHeaderView(0) // Obtén el nav header
        val textViewNavHeader = navHeader.findViewById<TextView>(R.id.tvIdNav)
        val textViewNavHeader2 = navHeader.findViewById<TextView>(R.id.tvCorreoNav)
        textViewNavHeader.text = usuario.Nombre
        textViewNavHeader2.text = usuario.Correo
        println("NO ACTUALIZADO: ${sharedViewModel.usuario.value}")
        sharedViewModel.usuario.value = usuario
        println("SE ha actualizado la informacion")
        println("ACTUALIZADO: ${sharedViewModel.usuario.value}")
    }

}
