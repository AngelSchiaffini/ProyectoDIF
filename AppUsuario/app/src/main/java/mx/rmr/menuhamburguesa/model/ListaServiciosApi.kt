package mx.rmr.menuhamburguesa.model

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListaServiciosApi
{
    @POST("/registrarUsuario")
    fun registrarUsuario(@Body nuevoUsuario:UsuarioR): Call<Any>

    @GET("/inicioSesion/{id}")
    fun iniciarSesionId(@Path("id") id: Int ): Call<List<Usuario>>

    @GET("/obtenerComedores")
    fun obtenerComedores(): Call<List<Comedores>>

    @POST("/calificarComedor")
    fun calificarComedor(@Body calificacionComedor: Calificacion): Call<Any>

    @GET("/obtenerFamilia/{id}")
    fun obtenerFamilia(@Path("id") id: Int): Call<List<Pariente>>

    @GET("/inicioSesion2/{curp}")
    fun iniciarSesionCURP(@Path("curp") curp: String): Call<List<Usuario>>

    @GET("/inicioSesion3/{celular}")
    fun iniciarSesionCel(@Path("celular") celular: String): Call<List<Usuario>>

    @GET("/inicioSesion4/{correo}")
    fun iniciarSesionCorreo(@Path("correo") correo: String): Call<List<Usuario>>

    @POST("/agregarPariente")
    fun agregarPariente(@Body idPariente: JsonObject): Call<Any>

    @PUT("/actualizarCorreo/{id}")
    fun actualizarCorreo(@Path("id") idUsuario: Int, @Body correoActualizado: JsonObject): Call<Any>

    @PUT("/actualizarNumeroTelefonico/{id}")
    fun actualizarNumero(@Path("id") idUsuario: Int, @Body numeroActualizado: JsonObject): Call<Any>

    @PUT("/actualizarCondicion/{id}")
    fun actualizarCondicion(@Path("id") idUsuario: Int, @Body condicionActualizada: JsonObject): Call<Any>

    @DELETE("/eliminarPariente/{Pariente1}/{Pariente2}")
    fun eliminarPariente(@Path("Pariente1") idPariente1: Int, @Path("Pariente2") idPariente2: Int): Call<Any>
}