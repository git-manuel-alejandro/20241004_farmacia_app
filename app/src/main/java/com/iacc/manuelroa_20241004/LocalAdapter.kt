package com.iacc.manuelroa_20241004

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class LocalAdapter(private val localList: List<JSONObject>) : RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {

    class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreLocal: TextView = itemView.findViewById(R.id.local_nombre)
        val direccionLocal: TextView = itemView.findViewById(R.id.local_direccion)
        val telefonoLocal: TextView = itemView.findViewById(R.id.local_telefono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local, parent, false)
        return LocalViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val local = localList[position]
        holder.nombreLocal.text = local.getString("local_nombre")
        holder.direccionLocal.text = local.getString("local_direccion")
        holder.telefonoLocal.text = local.getString("local_telefono")
    }

    override fun getItemCount(): Int {
        return localList.size
    }
}
