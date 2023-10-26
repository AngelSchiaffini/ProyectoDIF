package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.util.MurmurHash3
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.UsuarioR

class RegistrarNuevoUsuarioVM : ViewModel() {

    val idUsuario = MutableLiveData<Int>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    //modelo
    private val registro = APIS()

    fun resgitrarUsuarioVM(nuevoUsuario: UsuarioR){
        registro.registrarUsuario(nuevoUsuario)
        registro.idUsuarioNuevo.observeForever{
            idUsuario.value = it
        }
    }

}