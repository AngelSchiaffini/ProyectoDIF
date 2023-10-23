package mx.rmr.menuhamburguesa.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import mx.rmr.menuhamburguesa.R
import mx.rmr.menuhamburguesa.databinding.FragmentCambiarBinding
import mx.rmr.menuhamburguesa.databinding.FragmentHomeBinding
import mx.rmr.menuhamburguesa.databinding.FragmentRegistrarseBinding
import mx.rmr.menuhamburguesa.viewmodel.CambiarVM
import mx.rmr.menuhamburguesa.viewmodel.HomeViewModel
import mx.rmr.menuhamburguesa.viewmodel.SharedViewModel

class CambiarFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var _binding: FragmentCambiarBinding

    private val viewModel: CambiarVM by viewModels()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentCambiarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onStart() {
        super.onStart()
        registrarEventos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarSpinners()

    }

    private fun registrarSpinners() {
        // Spinner de Condicion
        val spinnerCondicion = requireView().findViewById<Spinner>(R.id.spnCondicionC)
        val opcionesCondicion = arrayOf(
            "Persona mayor de 60 años",
            "Menor de edad",
            "Persona indígena",
            "Persona con discapacidad",
            "Persona perteneciente al colectivo LGBTQ+",
            "Migrante o desplazado por conflictos",
            "Persona en condición de calle",
            "Mujer embarazada",
            "Trabajador/a informal",
            "Otra condición",
            "No aplica"
        )
        val adaptadorCondicion = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesCondicion)
        spinnerCondicion.adapter = adaptadorCondicion
    }

    private fun registrarEventos() {

        //Agregar pariente
        binding.btnAgregarPariente.setOnClickListener{
            val idParienteNuevo = binding.etIdParienteAgregar.text.toString().toInt()
            viewModel.agregarPariente(idParienteNuevo, sharedViewModel.usuario.value!!.IDUsuario)
            envioExitoso()
        }

        //Cambiar correo
        binding.btnCambiarCorreo.setOnClickListener {
            val correoActualizado = binding.etCorreoActualizado.text.toString()
            if (correoActualizado.contains("@")){
                viewModel.actualizarCorreo(sharedViewModel.usuario.value!!.IDUsuario, correoActualizado)
                envioExitoso()
            } else {
                envioNoExitoso()
            }
        }

        //Cambiar numero
        binding.btnCambiarTelefono.setOnClickListener {
            val numeroActualizado = binding.etTelefonoNuevo.text.toString()
            viewModel.actualizarNumero(sharedViewModel.usuario.value!!.IDUsuario, numeroActualizado)
            envioExitoso()
        }


        //Cambiar condicion
        binding.btnCambiarCondicion.setOnClickListener {
            val condicion = binding.spnCondicionC.selectedItem.toString()
            viewModel.actualizarCondicion(sharedViewModel.usuario.value!!.IDUsuario,condicion)
            envioExitoso()
        }

        //Eliminar Pariente
        binding.btnEliminarPariente.setOnClickListener {
            val idParienteBorrar = binding.etIdParienteEliminar.text.toString().toInt()
            viewModel.eliminarPariente(sharedViewModel.usuario.value!!.IDUsuario, idParienteBorrar)
            envioExitoso()
        }




    }

    private fun envioExitoso() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("Aviso")
            .setMessage("¡Información actualizada exitosamente!")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Cierra este fragmento y vuelve al anterior
                // requireActivity().supportFragmentManager.popBackStack()
            }
        //.create()
        alerta.show()
    }

    private fun envioNoExitoso() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("Aviso")
            .setMessage("¡El dato ingresado no cumple con el formato!")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Cierra este fragmento y vuelve al anterior
                // requireActivity().supportFragmentManager.popBackStack()
            }
        //.create()
        alerta.show()
    }


}