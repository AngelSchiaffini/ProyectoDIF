package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Asistencia
import mx.rmr.menuhamburguesaadmin.ui.model.Pariente
import mx.rmr.menuhamburguesaadmin.ui.model.Usuario

class InfoAsistenteVM : ViewModel() {

    val usuario = MutableLiveData<Usuario>()

    //Referencia al modelo
    private val usuarioRegistro = APIS()

    fun obtenerUsuario(id: Int){
        usuarioRegistro.iniciarSesionId(id)
        usuarioRegistro.usuario.observeForever(){
            usuario.value = it.toTypedArray()[0]
        }
    }

    fun registrarAsistencia(asistencia: Asistencia){
        usuarioRegistro.registrarAsistencia(asistencia)
    }

    //Referencia al modelo
    private val familia = APIS()


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