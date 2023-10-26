package mx.rmr.menuhamburguesa.model

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

class APIS {


    //LiveData
    val usuario = MutableLiveData<List<Usuario>>()
    val comedores = MutableLiveData<List<Comedores>>()
    val parientes = MutableLiveData<List<Pariente>>()
    val idUsuarioNuevo = MutableLiveData<Int>()

    //El objeto retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            //.baseUrl("http://44.217.43.137:8080/")
            .baseUrl("http://34.197.187.131:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Crea el objeto que instancia al objeto que hara la descarga de datos
    private val descargarAPI by lazy {
        retrofit.create(ListaServiciosApi::class.java)
    }

    fun registrarUsuario(nuevoUsuario: UsuarioR) {
        val call = descargarAPI.registrarUsuario(nuevoUsuario)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){

                    println("RESPUESTA: ${response.body()}")

                    val jsonStr = response.body()?.toString()

                    // Analiza el JSON
                    val jsonObject =  JSONObject(jsonStr.toString())

                    // Obtiene el valor entero del campo "numero" del JSON
                    val numero = jsonObject.getInt("IDUsuario")

                    idUsuarioNuevo.value = numero

                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    /////


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

    fun obtenerComedores(){
        val call = descargarAPI.obtenerComedores()
        call.enqueue(object: Callback<List<Comedores>>{
            override fun onResponse(call: Call<List<Comedores>>, response: Response<List<Comedores>>) {
                if (response.isSuccessful){
                    comedores.value = response.body()
                } else{
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Comedores>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    /////////

    fun calificarComedor(calificacionComedor: Calificacion){
        val call = descargarAPI.calificarComedor(calificacionComedor)
        call.enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("NICE")
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


    ////////


    fun iniciarSesionCURP(curp: String) {
        val call = descargarAPI.iniciarSesionCURP(curp)
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

    ///////

    fun iniciarSesionCel(celular: String) {
        val call = descargarAPI.iniciarSesionCel(celular)
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

    ///////

    fun iniciarSesionCorreo(correo: String) {
        val call = descargarAPI.iniciarSesionCorreo(correo)
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

    fun registrarPariente(idParienteNuevo: Int, idUsuario: Int) {

        //HACER JSON
        val jsonBody = JsonObject()
        jsonBody.addProperty("Pariente1", idUsuario)
        jsonBody.addProperty("Pariente2", idParienteNuevo)


        val call = descargarAPI.agregarPariente(jsonBody)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    ///////

    fun actualizarCorreo(idUsuario: Int, correoActualizado: String){

        //HACER JSON
        val jsonBody = JsonObject()
        jsonBody.addProperty("correoActualizado", correoActualizado)

        val call = descargarAPI.actualizarCorreo(idUsuario, jsonBody)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })


    }

    ///////

    fun actualizarNumero(idUsuario: Int, numeroActualizado: String){

        //HACER JSON
        val jsonBody = JsonObject()
        jsonBody.addProperty("numeroActualizado", numeroActualizado)

        val call = descargarAPI.actualizarNumero(idUsuario, jsonBody)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })

    }

    ///////

    fun actualizarCondicion(idUsuario: Int, condicionActualizado: String){

        //HACER JSON
        val jsonBody = JsonObject()
        jsonBody.addProperty("condicionActualizada", condicionActualizado)

        val call = descargarAPI.actualizarCondicion(idUsuario, jsonBody)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })

    }

    fun eliminarPariente(idUsuario: Int, idParienteBorrar: Int) {

        val call = descargarAPI.eliminarPariente(idUsuario, idParienteBorrar)
        call.enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful){
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("Error en la descarga ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })

    }


}