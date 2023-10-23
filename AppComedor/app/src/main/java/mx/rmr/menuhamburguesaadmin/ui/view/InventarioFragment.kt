package mx.rmr.menuhamburguesaadmin.ui.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.InventarioVM
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentInventarioBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Inventario
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.SharedViewModel

class InventarioFragment : Fragment() {


    var adaptadorInventario: RVInventario? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private  val viewModel: InventarioVM by viewModels()

    private var _binding: FragmentInventarioBinding? = null

    private val binding get() = _binding!!

    fun configurarRV(array: Array<Inventario>, contexto: Context) {
        val layout = LinearLayoutManager(contexto)
        layout.orientation = LinearLayoutManager.VERTICAL // Columna de renglones
        binding.rvInventario.layoutManager = layout
        // Adaptador
        adaptadorInventario = RVInventario(contexto, array)
        binding.rvInventario.adapter = adaptadorInventario // Conecta el adaptador al recicler view
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoVM =
            ViewModelProvider(this).get(InventarioVM::class.java)

        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
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
        configurarRV(emptyArray(),requireContext())
        sharedViewModel.comedor.observe(viewLifecycleOwner){
            viewModel.obtenerInventario(it.FolioComedor)
        }
    }

    private fun registrarObservables() {
        viewModel.inventarios.observe(viewLifecycleOwner){
            val inventario = it.toTypedArray()
            println(inventario[0])
            configurarRV(inventario,requireContext())
        }

    }

    override fun onStart() {
        super.onStart()
        registrarObservables()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}