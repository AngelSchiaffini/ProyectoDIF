package mx.rmr.menuhamburguesa.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.rmr.menuhamburguesa.R
import mx.rmr.menuhamburguesa.databinding.FragmentPrincipalBinding
import mx.rmr.menuhamburguesa.viewmodel.PrincipalVM

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

    // Crea la vista e inicializa los Eventos y Observables
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbInicioSesion.visibility = View.GONE
        registrarEventos()
        //registrarObservables()
    }

    // Registra los eventos
    private fun registrarEventos() {

        // Evento de cuando el usuario da clic en el botón del mapa
        binding.btnEntrar.setOnClickListener {

            val entrada = binding.etId.text.toString()

            val regexId = Regex("""^\d{4}$""")

            val regexCURP = Regex("^[A-Z0-9]{18}$")

            val regexCel = Regex("""^\d{10}$""")

            //ID

            if (regexId.matches(entrada)){
                val id = entrada.toInt()
                binding.pbInicioSesion.visibility = View.VISIBLE
                android.os.Handler().postDelayed({
                    viewModel.inicioSesionIDVM(id)
                    viewModel.usuario.observe(viewLifecycleOwner){
                        if (it != null){
                            val accion = PrincipalFragmentDirections.actionPrincipalFragmentToNavHome(it)
                            findNavController().navigate(accion)
                            println("SIUU")
                        } else{
                            println("NO HAY USUARIO JAJA")
                        }
                    }
                },4000)
            }

            //CURP
            else if (regexCURP.matches(entrada)){
                binding.pbInicioSesion.visibility = View.VISIBLE
                android.os.Handler().postDelayed({
                    viewModel.inicioSesionCURPVM(entrada)
                    viewModel.usuario.observe(viewLifecycleOwner){
                        if (it != null){
                            val accion = PrincipalFragmentDirections.actionPrincipalFragmentToNavHome(it)
                            findNavController().navigate(accion)
                            println("SIUU")
                        } else{
                            println("NO HAY USUARIO JAJA")
                        }
                    }
                },4000)
            }

            //CORREO
            else if( entrada.contains("@")){
                binding.pbInicioSesion.visibility = View.VISIBLE
                android.os.Handler().postDelayed({
                    viewModel.inicioSesionCorreoVM(entrada)
                    viewModel.usuario.observe(viewLifecycleOwner){
                        if (it != null){
                            val accion = PrincipalFragmentDirections.actionPrincipalFragmentToNavHome(it)
                            findNavController().navigate(accion)
                            println("SIUU")
                        } else{
                            println("NO HAY USUARIO JAJA")
                        }
                    }
                },4000)
            }

            //CELULAR

            else if(regexCel.matches(entrada)){
                binding.pbInicioSesion.visibility = View.VISIBLE
                android.os.Handler().postDelayed({
                    viewModel.inicioSesionCelVM(entrada)
                    viewModel.usuario.observe(viewLifecycleOwner){
                        if (it != null){
                            val accion = PrincipalFragmentDirections.actionPrincipalFragmentToNavHome(it)
                            findNavController().navigate(accion)
                            println("SIUU")
                        } else{
                            println("NO HAY USUARIO JAJA")
                        }
                    }
                },4000)
            }
            else{
                entradaIncompleta()
            }



        }
        binding.imgMapa.setOnClickListener {
            findNavController().navigate(R.id.action_principalFragment_to_mapaInicioFragment)
        }
        binding.txtRegistrarse.setOnClickListener {
            findNavController().navigate(R.id.action_principalFragment_to_registrarseFragment)

        }
    }

    private fun entradaIncompleta() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("Aviso")
            .setMessage("La entrada proporcionado no cuenta con ninguno de los formatos aceptados")
           // .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Cierra este fragmento y vuelve al anterior
                // requireActivity().supportFragmentManager.popBackStack()
            }
        //.create()
        alerta.show()
    }

}