package com.unab.pharmaliveapp

import Producto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val precio: TextView = itemView.findViewById(R.id.tvPrecio)
        val cantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val laboratorio: TextView = itemView.findViewById(R.id.tvLaboratorio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "Precio: $${producto.precio}"
        holder.cantidad.text = "Disponible: ${producto.cantidad}"
        holder.laboratorio.text = "Laboratorio: ${producto.laboratorio}"
    }

    override fun getItemCount(): Int = productos.size
}
