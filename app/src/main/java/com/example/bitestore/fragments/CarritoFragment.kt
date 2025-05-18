package com.example.bitestore.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitestore.Models.Producto
import com.example.bitestore.Adapters.ProductoAdapter
import com.example.bitestore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CarritoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val productosEnCarrito = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.carrito, container, false)

        recyclerView = view.findViewById(R.id.recycler_carrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        cargarCarrito()
        adapter = ProductoAdapter(productosEnCarrito) {} // Sin click listener
        recyclerView.adapter = adapter

        return view
    }

    private fun cargarCarrito() {
        val sharedPrefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)
        val json = sharedPrefs.getString("productos", null)

        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Producto>>() {}.type
            val listaGuardada = Gson().fromJson<MutableList<Producto>>(json, type)
            productosEnCarrito.addAll(listaGuardada)
        }
    }
}
