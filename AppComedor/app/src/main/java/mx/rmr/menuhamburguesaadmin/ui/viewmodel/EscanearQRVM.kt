package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import mx.rmr.menuhamburguesaadmin.ui.view.EscanearQRFragment

class EscanearQRVM : ViewModel() {

    val datoUsuario = MutableLiveData<String>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text



    fun scanner(context: Context) {
        val scanner = GmsBarcodeScanning.getClient(context)
        scanner.startScan()
            //Success
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                datoUsuario.value = rawValue.toString()
                //binding.tvResult.setText(rawValue.toString())
            }
            //Canceled
            .addOnCanceledListener {
                println("El escaneo ha sido cancelado")
                //binding.tvResult.setText("El escaneo ha sido cancelado")
            }
            //Failure
            .addOnFailureListener {
                println("El escaneo ha fallado")
                //binding.tvResult.setText("El escaneo ha fallado")
            }
    }

}