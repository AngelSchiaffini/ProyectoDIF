package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Inventario

class InventarioVM : ViewModel() {

    //Referencia al modelo
    private val inventario = APIS()

    val inventarios = MutableLiveData<List<Inventario>>()

    fun obtenerInventario(id: Int){
        inventario.obtenerInventario(id)
        inventario.inventario.observeForever{
            inventarios.value = it
        }
    }
}