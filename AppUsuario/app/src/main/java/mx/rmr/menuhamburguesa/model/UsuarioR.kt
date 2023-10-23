package mx.rmr.menuhamburguesa.model

import com.google.gson.annotations.SerializedName

data class UsuarioR(
    var NOMBRE: String,
    var APELLIDO1: String,
    var APELLIDO2: String,
    var CURP: String,
    var NACIONALIDAD: String,
    var SEXO: String,
    var FECHANAC: String,
    var CONDICION: String,
    var CEL: String,
    var CORREO: String
)
