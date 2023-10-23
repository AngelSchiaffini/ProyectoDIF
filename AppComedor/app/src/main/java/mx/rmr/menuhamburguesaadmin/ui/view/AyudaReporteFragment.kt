package mx.rmr.menuhamburguesaadmin.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.rmr.menuhamburguesaadmin.ui.viewmodel.AyudaReporteVM
import mx.rmr.menuhamburguesaadmin.R

class AyudaReporteFragment : Fragment() {

    companion object {
        fun newInstance() = AyudaReporteFragment()
    }

    private lateinit var viewModel: AyudaReporteVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ayuda_reporte, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AyudaReporteVM::class.java)
        // TODO: Use the ViewModel
    }

}