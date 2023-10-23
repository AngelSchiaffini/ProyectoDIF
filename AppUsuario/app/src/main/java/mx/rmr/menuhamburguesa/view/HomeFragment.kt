package mx.rmr.menuhamburguesa.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mx.rmr.menuhamburguesa.databinding.FragmentHomeBinding
import mx.rmr.menuhamburguesa.model.Usuario
import mx.rmr.menuhamburguesa.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var dataChangeListener: DataChangeListener? = null

    private var _binding: FragmentHomeBinding? = null

    private val args: HomeFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textView3
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onStart() {
        super.onStart()
        var usuarioActual = args.usuarioActual
        binding.tvBienvenida.setText("¡Hola, ${usuarioActual.Nombre}!")
        binding.tvIdUsuario.setText("No. Usuario ${usuarioActual.IDUsuario}")
        binding.tvNombreCompleto.setText("${usuarioActual.Nombre} ${usuarioActual.Apellido1} ${usuarioActual.Apellido2}")


        val qrCodeBitmap = viewModel.getQrCodeBitmap(usuarioActual)

//        val qrCodeImageView = findViewById<ImageView>(R.id.qrCodeImageView)
        binding.qrCodeImageView.setImageBitmap(qrCodeBitmap)
        someFunction()


    }


    interface DataChangeListener {
        fun onDataChanged(usuario: Usuario)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataChangeListener) {
            dataChangeListener = context
        } else {
            throw IllegalArgumentException("Activity must implement DataChangeListener")
        }
    }

    // Llama a esta función para actualizar los datos en el Navigation Drawer Header
    fun updateDataInNavHeader(usuario: Usuario) {
        dataChangeListener?.onDataChanged(usuario)
    }

    fun someFunction() {
        val usuario = args.usuarioActual
        updateDataInNavHeader(usuario)
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}