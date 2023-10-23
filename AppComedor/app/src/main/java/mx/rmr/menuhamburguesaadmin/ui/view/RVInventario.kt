package mx.rmr.menuhamburguesaadmin.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.ReciclerviewInventarioBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Inventario

class RVInventario(private val contexto: Context, var arrInventario: Array<Inventario>)
    : RecyclerView.Adapter<RVInventario.RenglonInventario> ()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonInventario {
        val binding = ReciclerviewInventarioBinding.inflate(LayoutInflater.from(contexto))
        return RenglonInventario(binding.root)
    }

    override fun getItemCount(): Int {
        return arrInventario.size
    }

    override fun onBindViewHolder(holder: RenglonInventario, position: Int) {
        val inventario = arrInventario[position]
        holder.set(inventario)
    }
    class RenglonInventario (var vistaRenglonInventario: View): RecyclerView.ViewHolder(vistaRenglonInventario){
        fun editarFecha(fecha: String): String{
            val fechaEnviar = fecha.substring(0,10)
            val diaFecha = fechaEnviar.substring(8,10)
            val mesFecha = fechaEnviar.substring(5,7)
            val yearFecha = fechaEnviar.substring(0,4)
            return "${diaFecha}-${mesFecha}-${yearFecha}"}
        fun set(inventario: Inventario){
            vistaRenglonInventario.findViewById<TextView>(R.id.tvCantidad).text = inventario.Cantidad.toString()
            vistaRenglonInventario.findViewById<TextView>(R.id.tvNombre).text = inventario.Nombre
            vistaRenglonInventario.findViewById<TextView>(R.id.tvDescripcion).text = inventario.Presentacion
            vistaRenglonInventario.findViewById<TextView>(R.id.tvFechadeCaducidad).text = editarFecha(inventario.FechaCad)
        }
    }
}