package mx.rmr.menuhamburguesaadmin.ui.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

class APIS {

    val comedorActual = MutableLiveData<List<Comedor>>()
    val calificaciones = MutableLiveData<List<Calificaciones>>()
    var gananciaDia = MutableLiveData<Int>()
    val usuario = MutableLiveData<List<Usuario>>()
    val inventario = MutableLiveData<List<Inventario>>()
    val parientes = MutableLiveData<List<Pariente>>()

    //El objeto retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.197.187.131:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Crea el objeto que instancia al objeto que hara la descarga de datos
    private val descargarAPI by lazy {
        retrofit.create(ListaServiciosAPI::class.java)
    }

    fun iniciarSesionComedor(usuario: String, contrasena: String){
        //HACER JSON
        val jsonBody = JsonObject()
        jsonBody.addProperty("Usuario", usuario)
        jsonBody.addProperty("Contrasena", contrasena)

        println(jsonBody)

        val call = descargarAPI.iniciarSesionComedor(jsonBody)
        call.enqueue(object: Callback<List<Comedor>>{
            override fun onResponse(call: Call<List<Comedor>>, response: Response<List<Comedor>>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                    comedorActual.value = response.body()
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Comedor>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }


    /////////


    fun obtenerCalificacionesSemanales(id: Int, inicio: Date, fin: Date){
        val call = descargarAPI.obtenerCalificacionesSemanales(id, inicio, fin)
        call.enqueue(object: Callback<List<Calificaciones>>{
            override fun onResponse(call: Call<List<Calificaciones>>, response: Response<List<Calificaciones>>) {
                if (response.isSuccessful){
                    calificaciones.value = response.body()
                    println("RESPUESTA: ${response.body()}")
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<Calificaciones>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }


    /////

    fun obtenerGananciasDia(id: Int, dia: Date){
        val call = descargarAPI.obtenerGananciasDia(id, dia)
        call.enqueue(object: Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }


    ////


    fun iniciarSesionId(id: Int) {
        val call = descargarAPI.iniciarSesionId(id)
        call.enqueue(object: Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                    usuario.value = response.body()
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    /////////

    fun registrarAsistencia(asistencia: Asistencia){
        val call = descargarAPI.registrarAsistencia(asistencia)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    ////////




    fun registrarUsuario(usuario: Usuario){
        val call = descargarAPI.registrarUsuario(usuario)
        call.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    ////////

    fun obtenerInventario(id: Int){
        val call = descargarAPI.obtenerInventario(id)
        call.enqueue(object: Callback<List<Inventario>>{
            override fun onResponse(
                call: Call<List<Inventario>>,
                response: Response<List<Inventario>>
            ) {
                if(response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                    inventario.value = response.body()
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<Inventario>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    //////

    fun enviarReporte(reporte: Reporte){
        val call = descargarAPI.enviarReporte(reporte)
        call.enqueue(object: Callback<Any>{
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if(response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    ////

    fun obtenerFamilia(id: Int){
        val call = descargarAPI.obtenerFamilia(id)
        call.enqueue(object: Callback<List<Pariente>>{
            override fun onResponse(
                call: Call<List<Pariente>>,
                response: Response<List<Pariente>>
            ) {
                if (response.isSuccessful){
                    parientes.value = response.body()
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<Pariente>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

}