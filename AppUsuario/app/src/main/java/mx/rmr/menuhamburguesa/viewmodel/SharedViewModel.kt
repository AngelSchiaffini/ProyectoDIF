package mx.rmr.menuhamburguesa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesa.model.Usuario

class SharedViewModel : ViewModel(){
    val usuario = MutableLiveData<Usuario>()
}