package mx.rmr.menuhamburguesa.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import mx.rmr.menuhamburguesa.databinding.FragmentCalficaComedoresBinding
import mx.rmr.menuhamburguesa.model.Calificacion
import mx.rmr.menuhamburguesa.viewmodel.CalificarComedorVM
import mx.rmr.menuhamburguesa.viewmodel.SharedViewModel
import java.util.Calendar

class CalificarComedorFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: CalificarComedorVM by viewModels()
    private var _binding: FragmentCalficaComedoresBinding? = null
    val selectedCalendar = Calendar.getInstance()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calificarComedorVM =
            ViewModelProvider(this).get(CalificarComedorVM::class.java)

        _binding = FragmentCalficaComedoresBinding.inflate(inflater, container, false)

        //Estrellas
        binding.rbRapidezComedor.rating = 0f
        binding.rbRapidezComedor.stepSize = 1f
        binding.rbComidaComedor.stepSize = 1f
        binding.rbLimpiezaComedor.stepSize = 1f

        val etFecha = binding.etFechaCalif
        etFecha.setOnClickListener { onClickScheduledDate(it) }

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.obtenerComedores()
        // Spinner de comedores
        viewModel.comedores.observe(viewLifecycleOwner){array->
            val arrComedores = array
            binding.spinnerComedores.adapter = ArrayAdapter( requireContext(),
                android.R.layout.simple_spinner_item, arrComedores)
        }

        registrarEventos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun registrarEventos() {
        binding.btnCalificarComedor.setOnClickListener{
            var calificacion = Calificacion(sharedViewModel.usuario.value!!.IDUsuario,binding.spinnerComedores.selectedItem.toString(), binding.etFechaCalif.text.toString(),binding.rbLimpiezaComedor.rating.toInt(),
                binding.rbComidaComedor.rating.toInt(), binding.rbRapidezComedor.rating.toInt(), binding.etComentarioComedor.text.toString())
            viewModel.calificarComedor(calificacion)
            envioCalificacionExitoso()
        }
    }

    private fun onClickScheduledDate(it: View?) {
        val etScheduleDate = binding.etFechaCalif

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            selectedCalendar.set(y, m, d)
            val formattedDate = String.format("%02d-%02d-%d", y, m + 1, d) // Formato aaaa-mm-dd
            etScheduleDate.setText(formattedDate)
        }
        DatePickerDialog(requireContext(), listener, year, month, day).show()
    }

    private fun envioCalificacionExitoso() {
        val alerta = AlertDialog.Builder(requireContext())
            .setTitle("Aviso")
            .setMessage("Calificación enviada correctamente!")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Cierra este fragmento y vuelve al anterior
               // requireActivity().supportFragmentManager.popBackStack()
            }
            //.create()
        alerta.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



