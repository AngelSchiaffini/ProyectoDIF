package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.APIS
import mx.rmr.menuhamburguesa.model.Comedores

class MenuVM : ViewModel() {


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

}