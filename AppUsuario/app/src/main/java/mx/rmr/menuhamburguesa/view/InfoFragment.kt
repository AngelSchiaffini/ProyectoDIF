package mx.rmr.menuhamburguesa.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mx.rmr.menuhamburguesa.R
import mx.rmr.menuhamburguesa.databinding.FragmentInfoBinding
import mx.rmr.menuhamburguesa.model.Pariente
import mx.rmr.menuhamburguesa.viewmodel.InfoVM
import mx.rmr.menuhamburguesa.model.RVParientes
import mx.rmr.menuhamburguesa.viewmodel.SharedViewModel

class InfoFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    var adaptadorPariente: RVParientes? = null

    private val viewModel: InfoVM by viewModels()

    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    fun configurarRV(array: Array<Pariente>, contexto: Context){
        val layout = LinearLayoutManager(contexto)
        layout.orientation = LinearLayoutManager.VERTICAL // Columna de renglones
        binding.rvParientes.layoutManager = layout
        // Adaptador
        adaptadorPariente = RVParientes(contexto, array)
        binding.rvParientes.adapter = adaptadorPariente // Conecta el adaptador al recicler view

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoVM =
            ViewModelProvider(this).get(InfoVM::class.java)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.t
//        infoVM.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        configurarRV(emptyArray(),requireContext())
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Accede a los datos del usuario desde el ViewModel compartido
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.usuario.observe(viewLifecycleOwner){
            viewModel.obtenerFamilia(it.IDUsuario)
            registrarObservables()
        }
    }
    private fun registrarObservables() {
        viewModel.parientes.observe(viewLifecycleOwner){
            println(it)
            val parientes = it.toTypedArray()
            configurarRV(parientes,requireContext())
        }

        binding.btnMasInfo.setOnClickListener() {
            findNavController().navigate(R.id.action_consultarInfo_to_cambiarFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}