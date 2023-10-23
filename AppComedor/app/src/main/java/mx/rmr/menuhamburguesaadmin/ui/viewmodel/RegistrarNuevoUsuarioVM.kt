package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Usuario

class RegistrarNuevoUsuarioVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    //modelo
    private val registro = APIS()

    fun resgitrarUsuarioVM(nuevoUsuario: Usuario){
        registro.registrarUsuario(nuevoUsuario)
    }

}