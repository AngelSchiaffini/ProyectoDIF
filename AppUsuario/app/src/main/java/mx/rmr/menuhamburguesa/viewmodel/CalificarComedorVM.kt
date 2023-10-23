package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.APIS
import mx.rmr.menuhamburguesa.model.Calificacion
import mx.rmr.menuhamburguesa.model.Comedores

class CalificarComedorVM : ViewModel() {

    //comedores
    val comedores = MutableLiveData<Array<Comedores>>()


    //Referencia al modelo
    private val calificacion = APIS()

    fun obtenerComedores(){
        calificacion.obtenerComedores()
        calificacion.comedores.observeForever(){lista->
            comedores.value = lista.toTypedArray()
        }
    }

    fun calificarComedor(calificacionComedor: Calificacion){
        calificacion.calificarComedor(calificacionComedor)
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
}