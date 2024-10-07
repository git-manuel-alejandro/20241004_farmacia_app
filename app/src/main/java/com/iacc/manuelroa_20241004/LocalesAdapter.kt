package com.iacc.manuelroa_20241004

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class Local(
    val local_nombre: String,
    val comuna_nombre: String,
    val local_direccion: String,
    val local_telefono: String
)


class LocalesAdapter(private val listaLocales: List<Local>) :
    RecyclerView.Adapter<LocalesAdapter.LocalViewHolder>() {


    class LocalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreLocal: TextView = view.findViewById(R.id.local_nombre)
        val comunaLocal: TextView = view.findViewById(R.id.comuna_nombre)
        val direccionLocal: TextView = view.findViewById(R.id.local_direccion)
        val telefonoLocal: TextView = view.findViewById(R.id.local_telefono)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_local, parent, false)
        return LocalViewHolder(view)
    }


    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val local = listaLocales[position]

        holder.nombreLocal.text = local.local_nombre
        holder.comunaLocal.text = local.comuna_nombre
        holder.direccionLocal.text = local.local_direccion
        holder.telefonoLocal.text = local.local_telefono
    }


    override fun getItemCount(): Int = listaLocales.size
}
