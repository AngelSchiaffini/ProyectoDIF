package mx.rmr.menuhamburguesaadmin.ui.view

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
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.ActivityMainBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Comedor
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity(), CalificacionesFragment.DataChangeListener {

    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val selectedCalendar = Calendar.getInstance()
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
                R.id.nav_calificaciones, R.id.nav_escanearqr, R.id.nav_registrarnuevousuario,
                R.id.nav_inventario, R.id.nav_menu, R.id.nav_reportes
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Agrega este bloque de código
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    println("Se salio we")
                    seguroDeSalir()
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

    private fun seguroDeSalir() {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDataChanged(comedor: Comedor) {
        val navView: NavigationView = binding.navView
        val navHeader = navView.getHeaderView(0) // Obtén el nav header
        val textViewNavHeader = navHeader.findViewById<TextView>(R.id.tvNombreComedorNav)
        val textViewNavHeader2 = navHeader.findViewById<TextView>(R.id.tvFolioComedorNav)
        textViewNavHeader.text = comedor.Nombre
        val folioComedor = "Folio No. ${comedor.FolioComedor}"
        textViewNavHeader2.text = folioComedor
        println("NO ACTUALIZADO: ${sharedViewModel.comedor}")
        sharedViewModel.comedor.value = comedor
        println("ACTUALIZADO: ${sharedViewModel.comedor}")
    }

}