package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Comedor

class PrincipalVM : ViewModel() {

    val comedorActual = MutableLiveData<Comedor>()

    //Referencia al modelo
    private val comedor = APIS()

    fun iniciarSesionVM(usuario: String, contrasena: String){
        comedor.iniciarSesionComedor(usuario, contrasena)
        comedor.comedorActual.observeForever(){
            comedorActual.value = it.toTypedArray()[0]
        }
    }


}