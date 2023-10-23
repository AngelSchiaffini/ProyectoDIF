package mx.rmr.menuhamburguesaadmin.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.rmr.menuhamburguesaadmin.R
import mx.rmr.menuhamburguesaadmin.databinding.ReciclerviewParientesBinding
import mx.rmr.menuhamburguesaadmin.ui.model.Pariente


class RVParientes(private val contexto: Context, var arrParientes: Array<mx.rmr.menuhamburguesaadmin.ui.model.Pariente>)
    : RecyclerView.Adapter< RVParientes.RenglonPariente > ()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonPariente {
        val binding = ReciclerviewParientesBinding.inflate(LayoutInflater.from(contexto))
        return RenglonPariente(binding.root)
    }

    override fun getItemCount(): Int {
        return arrParientes.size
    }

    override fun onBindViewHolder(holder: RenglonPariente, position: Int) {
        val pariente = arrParientes[position]
        holder.set(pariente)
    }

    class RenglonPariente(var vistaRenglonPariente: View): RecyclerView.ViewHolder(vistaRenglonPariente){
        fun set(pariente: Pariente){
            vistaRenglonPariente.findViewById<TextView>(R.id.cbPariente).text = "${pariente.Nombre} ${pariente.Apellido1} ${pariente.Apellido2}"
        }
    }

}

//class Pariente {
//
//}
