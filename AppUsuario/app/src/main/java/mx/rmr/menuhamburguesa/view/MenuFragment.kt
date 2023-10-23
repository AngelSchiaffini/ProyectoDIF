package mx.rmr.menuhamburguesa.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.rmr.menuhamburguesa.viewmodel.MenuVM
import mx.rmr.menuhamburguesa.R
import mx.rmr.menuhamburguesa.databinding.FragmentMenuBinding
import mx.rmr.menuhamburguesa.model.Menu
import mx.rmr.menuhamburguesa.viewmodel.SharedViewModel

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
            ViewModelProvider(this).get(MenuVM::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun escucharObservadores() {
        binding.btnMenu.setOnClickListener{
            menuComedor(binding.spComedores.selectedItem.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.obtenerComedores()
         //Spinner de comedores
        viewModel.comedores.observe(viewLifecycleOwner){array->
            val arrComedores = array
            binding.spComedores.adapter = ArrayAdapter( requireContext(),
                android.R.layout.simple_spinner_item, arrComedores)
        }
                escucharObservadores()
//        // Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("autor")
//        myRef.setValue("Joahan Javier Garcia Fernandez")
    }

     fun menuComedor(nombre: String) {
        val baseDatos = Firebase.database
        val ref = baseDatos.getReference("/MenuComedores/${nombre}")
        // Solicitar datos (Asíncrona)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val entrada1 = snapshot.child("entrada1").getValue(String::class.java)
                val entrada2 = snapshot.child("entrada2").getValue(String::class.java)
                val platoFuerte = snapshot.child("platoFuerte").getValue(String::class.java)
                val postre = snapshot.child("postre").getValue(String::class.java)
                val bebida = snapshot.child("bebida").getValue(String::class.java)

                binding.tvEntrada1.text = entrada1
                binding.tvEntrada2.text = entrada2
                binding.tvPlatilloFuerte.text = platoFuerte
                binding.tvPostre.text = postre
                binding.tvBebida.text = bebida

            }

            override fun onCancelled(error: DatabaseError) {
                println("Error en la descarga...")
            }
        })
    }


}