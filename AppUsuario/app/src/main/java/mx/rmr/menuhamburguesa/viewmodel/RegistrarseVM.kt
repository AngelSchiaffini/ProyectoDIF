package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.APIS
import mx.rmr.menuhamburguesa.model.Usuario
import mx.rmr.menuhamburguesa.model.UsuarioR

class RegistrarseVM : ViewModel() {

    //
    val usuario = MutableLiveData<Usuario>()

    //modelo
    private val registro = APIS()
    fun resgitrarUsuarioVM(nuevoUsuario: UsuarioR){
        registro.registrarUsuario(nuevoUsuario)
        registro.idUsuarioNuevo.observeForever{
            registro.iniciarSesionId(it)
            registro.usuario.observeForever{usuarioNuevo ->
                usuario.value = usuarioNuevo[0]
            }
        }
    }

}