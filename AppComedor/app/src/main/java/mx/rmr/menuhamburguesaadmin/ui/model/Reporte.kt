package mx.rmr.menuhamburguesaadmin.ui.model

import java.util.Date

data class Reporte(
    var FolioComedor: Int,
    var Fecha: Date,
    var Tipo: String,
    var Descripcion: String,
    var Urgencia: String,
    var Estado: Int
)
