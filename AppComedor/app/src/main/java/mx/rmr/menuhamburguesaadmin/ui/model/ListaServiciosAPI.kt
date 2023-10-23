package mx.rmr.menuhamburguesaadmin.ui.model

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

interface ListaServiciosAPI {

    @POST("/iniciarSesionComedor")
    fun iniciarSesionComedor(@Body datosComedor: JsonObject): Call<List<Comedor>>

    @GET("/calificacionesSemanales/{id}/{inicio}/{fin}")
    fun obtenerCalificacionesSemanales(@Path("id") id: Int, @Path("inicio") inicio: Date, @Path("fin") fin: Date): Call<List<Calificaciones>>

    @GET("/gananciaDia/{id}/{dia}")
    fun obtenerGananciasDia(@Path("id") id: Int, @Path("dia") dia: Date): Call<Int>

    @GET("/inicioSesion/{id}")
    fun iniciarSesionId(@Path("id") id: Int ): Call<List<Usuario>>

    @POST("/registrarAsistencia")
    fun registrarAsistencia(@Body asistencia: Asistencia): Call<Any>

    @POST("/registrarUsuario")
    fun registrarUsuario(@Body nuevoUsuario:Usuario): Call<Any>

    @GET("/obtenerInventario/{id}")
    fun obtenerInventario(@Path("id") id: Int): Call<List<Inventario>>

    @POST("/enviarReporte")
    fun enviarReporte(@Body reporte: Reporte): Call<Any>

    @GET("/obtenerFamilia/{id}")
    fun obtenerFamilia(@Path("id") id: Int): Call<List<Pariente>>


}