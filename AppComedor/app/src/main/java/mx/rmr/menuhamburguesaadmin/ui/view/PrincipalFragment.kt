package mx.rmr.menuhamburguesaadmin.ui.view

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.PrincipalVM
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentPrincipalBinding
import java.util.Calendar

class PrincipalFragment : Fragment() {

    private val viewModel: PrincipalVM by viewModels()

    private lateinit var binding: FragmentPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrincipalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbInicioSesion.visibility = View.GONE
        registrarEventos()
        //registrarObservables()
    }

    // Registra los eventos
    private fun registrarEventos() {

        // Evento de cuando el usuario da clic en el botón del mapa
        binding.btnIngresar.setOnClickListener{
            val usuario = binding.etUsuario.text.toString()
            val contrasena = binding.etContrasena.text.toString()
            binding.pbInicioSesion.visibility = View.VISIBLE
            android.os.Handler().postDelayed({
                viewModel.iniciarSesionVM(usuario, contrasena)
                viewModel.comedorActual.observe(viewLifecycleOwner){
                    if (it != null){
                        val accion = PrincipalFragmentDirections.actionPrincipalFragmentToNavCalificaciones(it)
                        findNavController().navigate(accion)
                    } else{
                        alertaComedor()
                        println("NO HAY Comdero JAJA")
                    }
                }
            },4000)
        }
    }

    private fun alertaComedor() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("AVISO")
            .setMessage("El comedor no existe")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        alerta.show()
    }
}