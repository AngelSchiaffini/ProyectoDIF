package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.APIS
import mx.rmr.menuhamburguesa.model.Pariente
import mx.rmr.menuhamburguesa.model.Usuario

class InfoVM : ViewModel() {

    //Referencia al modelo
    private val familia = APIS()

    val usuario = MutableLiveData<Usuario>()

    val parientes = MutableLiveData<List<Pariente>>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    fun obtenerFamilia(id: Int){
        familia.obtenerFamilia(id)
        familia.parientes.observeForever(){ lista ->
            parientes.value = lista
        }
    }


}