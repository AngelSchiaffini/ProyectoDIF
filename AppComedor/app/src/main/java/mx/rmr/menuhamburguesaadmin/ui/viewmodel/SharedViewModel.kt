package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.Comedor

class SharedViewModel: ViewModel() {

    val comedor = MutableLiveData<Comedor>()

}