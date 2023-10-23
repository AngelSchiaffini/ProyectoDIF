package mx.rmr.menuhamburguesaadmin.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.rmr.menuhamburguesaadmin.ui.model.APIS
import mx.rmr.menuhamburguesaadmin.ui.model.Calificaciones
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date





class CalificacionesVM : ViewModel() {

    val calificacionesComedorActual = MutableLiveData<Calificaciones>()
    val gananciasDia = MutableLiveData<Int>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    //Referencia al modelo
    private val calificaciones = APIS()


    // Obtén la fecha actual como LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualLocalDate = LocalDate.now()

    // Convierte LocalDate a java.util.Date
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaActualDate: Date = Date.from(fechaActualLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

    // Obtén la fecha de hace 7 días como LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaHaceSieteDiasLocalDate = fechaActualLocalDate.minusDays(7)

    // Convierte LocalDate a java.util.Date
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaHaceSieteDiasDate: Date = Date.from(fechaHaceSieteDiasLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())


    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerCalificacionesSemanales(id: Int){
        calificaciones.obtenerCalificacionesSemanales(id,fechaHaceSieteDiasDate,fechaActualDate)
        calificaciones.calificaciones.observeForever(){
            calificacionesComedorActual.value = it[0]
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerGananciasDia(id: Int){
        calificaciones.obtenerGananciasDia(id, fechaActualDate)
        calificaciones.gananciaDia.observeForever(){
            calificaciones.gananciaDia.value = it
        }
    }


}