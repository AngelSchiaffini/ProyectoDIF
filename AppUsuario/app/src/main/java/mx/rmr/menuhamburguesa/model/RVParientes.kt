package mx.rmr.menuhamburguesa.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.rmr.menuhamburguesa.R
import android.view.View
import mx.rmr.menuhamburguesa.databinding.RecyclerviewInfoBinding


class RVParientes(private val contexto: Context, var arrParientes: Array<Pariente>)
    : RecyclerView.Adapter<RVParientes.RenglonPariente> ()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonPariente {
        val binding = RecyclerviewInfoBinding.inflate(LayoutInflater.from(contexto))
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
            vistaRenglonPariente.findViewById<TextView>(R.id.tvPariente).text = "${pariente.Nombre} ${pariente.Apellido1} ${pariente.Apellido2}"
        }
    }

    }