package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.APIS

class CambiarVM : ViewModel() {

    //Referencia al modelo
    private val actualizar = APIS()

    fun agregarPariente(idParienteNuevo: Int, idUsuario: Int) {
        actualizar.registrarPariente(idParienteNuevo, idUsuario)
    }

    fun actualizarCorreo(idUsuario: Int, correoActualizado: String) {
        actualizar.actualizarCorreo(idUsuario, correoActualizado)
    }

    fun actualizarNumero(idUsuario: Int, numeroActualizado: String) {
        actualizar.actualizarNumero(idUsuario, numeroActualizado)
    }

    fun actualizarCondicion(idUsuario: Int, condicionActualizada: String){
        actualizar.actualizarCondicion(idUsuario, condicionActualizada)
    }

    fun eliminarPariente(idUsuario: Int, idParienteBorrar: Int) {
        actualizar.eliminarPariente(idUsuario, idParienteBorrar)
    }

}