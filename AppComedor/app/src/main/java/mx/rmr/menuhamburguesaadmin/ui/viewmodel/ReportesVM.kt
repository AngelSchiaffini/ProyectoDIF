package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Reporte

class ReportesVM : ViewModel() {

    //Referencia al modelo
    private val reportes = APIS()

    fun enviarReporte(reporte: Reporte){
        reportes.enviarReporte(reporte)
    }
}