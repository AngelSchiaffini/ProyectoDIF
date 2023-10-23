package mx.rmr.menuhamburguesaadmin.ui.view

import android.os.Bundle
import android.print.PrintJobInfo.STATE_COMPLETED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED
import com.google.android.gms.tflite.java.TfLite
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.FragmentEscanearqrBinding
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.EscanearQRVM
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.PrincipalVM

class EscanearQRFragment : Fragment() {

    private val viewModel: EscanearQRVM by viewModels()

    private var _binding:FragmentEscanearqrBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val escanearQRVM =
            ViewModelProvider(this).get(EscanearQRVM::class.java)

        _binding = FragmentEscanearqrBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        //registrarObservables()
    }

    // Registra los eventos

    private fun registrarEventos() {

        // Evento de cuando el usuario da clic en el botón del mapa
        binding.btnEscanear.setOnClickListener{
            viewModel.scanner(requireContext())
            viewModel.datoUsuario.observe(viewLifecycleOwner){
                val accion = EscanearQRFragmentDirections.actionNavEscanearqrToInfoAsistenteFragment(it)
                findNavController().navigate(accion)
            }
            //findNavController().navigate(R.id.action_nav_escanearqr_to_infoAsistenteFragment)
        }
        binding.btnEscaneaManual.setOnClickListener{
            val datoUsuario = binding.tiCURP.text.toString()
            val accion = EscanearQRFragmentDirections.actionNavEscanearqrToInfoAsistenteFragment(datoUsuario)
            findNavController().navigate(accion)
        }
    }


}