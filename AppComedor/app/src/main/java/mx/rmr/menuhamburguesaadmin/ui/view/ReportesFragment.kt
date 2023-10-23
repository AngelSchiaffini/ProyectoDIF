package mx.rmr.menuhamburguesaadmin.ui.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentReportesBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Reporte
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.EscanearQRVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.ReportesVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class ReportesFragment : Fragment() {

    private val viewModel: ReportesVM by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentReportesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarSpinners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        registrarEventos()
    }

    private fun registrarSpinners() {
        // Spinner tipo reporte
        val spinnerTipoReporte = requireView().findViewById<Spinner>(R.id.spinnerTipoReportes)
        val opcionesTipoReporte = arrayOf(
            "Reporte de seguridad",
            "Reporte de salud e higiene",
            "Reporte de incidentes",
            "Reporte de mantenimiento"
        )
        val adaptadorTipoReporte= ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesTipoReporte)
        spinnerTipoReporte.adapter = adaptadorTipoReporte

        // Spinner urgencia
        val spinnerUrgencia = requireView().findViewById<Spinner>(R.id.spinnerUrgencia)
        val opcionesUrgencia = arrayOf(
            "Inmediato",
            "Menos de 30 minutos",
            "Menos de 2 horas",
            "Antes de 24 horas",
            "Antes de 72 horas",
            "Sin rango de asignado"
        )
        val adaptadorUrgencia= ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesUrgencia)
        spinnerUrgencia.adapter = adaptadorUrgencia
    }

    // Obtén la fecha actual como LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualLocalDate = LocalDate.now()

    // Convierte LocalDate a java.util.Date
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualDate: Date = Date.from(fechaActualLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarEventos() {
        binding.btnEnviarReporte.setOnClickListener{
            sharedViewModel.comedor.observe(viewLifecycleOwner){
                viewModel.enviarReporte(
                    Reporte( it.FolioComedor, fechaActualDate, binding.spinnerTipoReportes.selectedItem.toString(),
                    binding.etDescripcion.text.toString(), binding.spinnerUrgencia.selectedItem.toString(), 0))
                alertaReporte()
            }
        }
        binding.btnAyuda.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_reportes_to_ayudaReporteFragment)
        }
    }

    private fun alertaReporte() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("AVISO")
            .setMessage("Reporte enviado correctamente")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        alerta.show()
    }

}