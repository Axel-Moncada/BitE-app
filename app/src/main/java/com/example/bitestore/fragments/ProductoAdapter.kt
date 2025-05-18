package com.example.bitestore.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitestore.R
import com.example.bitestore.Models.Producto

class ProductoAdapter (
    private val productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    override fun getItemCount(): Int = productos.size + 1 // +1 por el header

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_producto, parent, false)
            ProductoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductoViewHolder) {
            val producto = productos[position - 1] // -1 porque header está en pos 0
            holder.bind(producto)
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagenProducto: ImageView = itemView.findViewById(R.id.imagen_producto)
        private val nombreProducto: TextView = itemView.findViewById(R.id.nombre_producto)
        private val precioProducto: TextView = itemView.findViewById(R.id.precio_producto)

        fun bind(producto: Producto) {
            imagenProducto.setImageResource(producto.imagenResourceId)
            nombreProducto.text = producto.nombre
            precioProducto.text = "$${producto.precio}"

            itemView.setOnClickListener {
                onProductoClick(producto)
            }
        }
    }

}