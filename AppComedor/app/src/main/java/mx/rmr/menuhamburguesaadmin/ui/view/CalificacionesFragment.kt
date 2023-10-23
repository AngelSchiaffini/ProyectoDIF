package mx.rmr.menuhamburguesaadmin.ui.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mx.rmr.menuhamburguesaadmin.databinding.FragmentCalificacionesBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Comedor
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.CalificacionesVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.PrincipalVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel

class CalificacionesFragment : Fragment() {

    private var dataChangeListener: DataChangeListener? = null

    private var _binding: FragmentCalificacionesBinding? = null

    private val args: CalificacionesFragmentArgs by navArgs()

    private val viewModel: CalificacionesVM by viewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calificacionesVM =
            ViewModelProvider(this).get(CalificacionesVM::class.java)

        _binding = FragmentCalificacionesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.text
//        calificacionesVM.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        binding.tvNombreComedorNav.text = args.comedor.Nombre
        viewModel.obtenerCalificacionesSemanales(args.comedor.FolioComedor)
        someFunction()
        viewModel.calificacionesComedorActual.observe(viewLifecycleOwner){
            if (it != null){

                println(it.PromedioAtencion.toFloat())
                println(it.PromedioComida.toFloat())
                println(it.PromedioLimpieza.toFloat())
                binding.rbAtencionComedor.rating = it.PromedioAtencion.toFloat()
                binding.rbComidaComedor.rating = it.PromedioComida.toFloat()
                binding.rbLimpiezaComedor.rating = it.PromedioLimpieza.toFloat()
                binding.tvAtencion.text = it.PromedioAtencion.toString()
                binding.tvComida.text = it.PromedioComida.toString()
                binding.tvLimpieza.text = it.PromedioLimpieza.toString()

            } else{
                println("NOOO")
            }
        }
        viewModel.obtenerGananciasDia(args.comedor.FolioComedor)

//        viewModel.gananciasDia.observe(viewLifecycleOwner){
//            if (it != null){
//                println(it)
//                binding.tvGanancia.text = it.toString()
//            }
//        }


    }

    interface DataChangeListener {
        fun onDataChanged(comedor: Comedor)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataChangeListener) {
            dataChangeListener = context
        } else {
            throw IllegalArgumentException("Activity must implement DataChangeListener")
        }
    }

    fun updateDataInNavHeader(comedor: Comedor) {
        dataChangeListener?.onDataChanged(comedor)
    }

    fun someFunction() {
        val comedor = args.comedor
        updateDataInNavHeader(comedor)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}