package mx.rmr.menuhamburguesaadmin.ui.view

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.InfoAsistenteVM
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentEscanearqrBinding
import mx.rmr.menuhamburguesaadmin.databinding.FragmentInfoAsistenteBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Asistencia
import mx.rmr.menuhamburguesaadmin.ui.model.Pariente
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class InfoAsistenteFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentInfoAsistenteBinding? = null

    private val args: InfoAsistenteFragmentArgs by navArgs()

    private val binding get() = _binding!!

    var idUsuario: Int = 0

    companion object {
        fun newInstance() = InfoAsistenteFragment()
    }

    private lateinit var viewModel: InfoAsistenteVM

    var adaptadorPariente: RVParientes? = null

    fun configurarRV(array: Array<Pariente>, contexto: Context){
        val layout = LinearLayoutManager(contexto)
        layout.orientation = LinearLayoutManager.VERTICAL // Columna de renglones
        binding.rvParientes.layoutManager = layout
        // Adaptador
        adaptadorPariente = RVParientes(contexto, array)
        binding.rvParientes.adapter = adaptadorPariente // Conecta el adaptador al recicler view

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentInfoAsistenteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoAsistenteVM::class.java)
        // TODO: Use the ViewModel
    }

    // Obtén la fecha actual como LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualLocalDate = LocalDate.now()

    // Convierte LocalDate a java.util.Date
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualDate: Date = Date.from(fechaActualLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val datoUsuario = args.datoManual
        if (datoUsuario.length == 4){
            viewModel.obtenerUsuario(datoUsuario.toInt())
            viewModel.usuario.observe(viewLifecycleOwner){
                val nombreCompleto = "${it.Nombre} ${it.Apellido1} ${it.Apellido2}"
                val fechaEnviar = it.FechaNac?.substring(0,10)
                val diaFecha = fechaEnviar?.substring(8,10)
                val mesFecha = fechaEnviar?.substring(5,7)
                val yearFecha = fechaEnviar?.substring(0,4)
                val fechaMostrar = "${diaFecha}-${mesFecha}-${yearFecha}"
                binding.tvNombreCompleto.text = nombreCompleto
                binding.tvFechaNac.text = fechaMostrar
                binding.tvCondicion.text = it.Condicion
                idUsuario = it.IDUsuario
                viewModel.obtenerFamilia(idUsuario)
                viewModel.parientes.observe(viewLifecycleOwner){
                    println(it)
                    val parientes = it.toTypedArray()
                    configurarRV(parientes,requireContext())
                }
            }
        } else{
            println("ES  CURP")
        }
        registrarEventos()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarEventos() {
        binding.btnRegistrar.setOnClickListener{
            println("Aqui si")
            println(sharedViewModel.comedor.value)
            sharedViewModel.comedor.observeForever(){
                var donada: Int = 0
                if(binding.cbDonada.isChecked){
                    donada = 1
                } else{
                    donada = 0
                }
                println(it.FolioComedor)
                val asistencia = Asistencia(fechaActualDate ,donada, idUsuario, it.FolioComedor)
                viewModel.registrarAsistencia(asistencia)
                envioCalificacionExitoso()
            }
        }
    }


    private fun envioCalificacionExitoso() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("Aviso")
            .setMessage("¡Asistencia registrada, provecho!")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Cierra este fragmento y vuelve al anterior
                // requireActivity().supportFragmentManager.popBackStack()
            }
        //.create()
        alerta.show()
    }

}