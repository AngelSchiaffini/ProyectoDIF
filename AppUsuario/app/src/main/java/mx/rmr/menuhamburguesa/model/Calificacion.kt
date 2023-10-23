package mx.rmr.menuhamburguesa.model

data class Calificacion(
    val IDUsuario: Int,
    val NombreComedor: String,
    val Fecha: String,
    val CalLimpieza: Int,
    val CalComida: Int,
    val CalAtencion: Int,
    val Comentario: String?
)
