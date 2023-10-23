package mx.rmr.menuhamburguesaadmin.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.MenuVM
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentMenuBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Menu
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.InventarioVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel

class MenuFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var baseDatos: FirebaseDatabase? = null

    private val viewModel: MenuVM by viewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoVM =
            ViewModelProvider(this).get(InventarioVM::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }




    override fun onStart() {
        super.onStart()
//        // Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("autor")
//        myRef.setValue("Joahan Javier Garcia Fernandez")
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnEnviarMenu.setOnClickListener{
            val entrada1 = binding.etEntrada1.text.toString()
            val entrada2 = binding.etEntrada2.text.toString()
            val platoFuerte = binding.etPlatoFuerte.text.toString()
            val postre = binding.etPostre.text.toString()
            val bebida = binding.etBebida.text.toString()
            if (entrada1 != null ){
                val Menu = Menu(entrada1, entrada2, platoFuerte,postre,bebida)
                //Grabar en DB
                val baseDatos = Firebase.database
                val ref = baseDatos.getReference("/MenuComedores/${sharedViewModel.comedor.value?.Nombre}")
                ref.setValue(Menu)
                alertaMenu()
            }
        }
    }

    private fun alertaMenu() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("AVISO")
            .setMessage("Menu enviado correctamente")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        alerta.show()
    }

}