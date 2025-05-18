package com.example.bitestore.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bitestore.R
import com.example.bitestore.Models.Producto

import com.google.gson.Gson



class DetalleProductoFragment : Fragment () {

    private lateinit var producto: Producto

    companion object {
        private const val ARG_PRODUCTO = "producto"

        fun newInstance(producto: Producto): DetalleProductoFragment {
            val fragment = DetalleProductoFragment()
            val args = Bundle()
            // Usamos putParcelable para serializacion
            args.putParcelable(ARG_PRODUCTO, producto)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
                       producto = if (android.os.Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(ARG_PRODUCTO, Producto::class.java)
                    ?: Producto(0, "Sin nombre", "Sin descripción", 0.0, R.drawable.placeholder)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable(ARG_PRODUCTO)
                    ?: Producto(0, "Sin nombre", "Sin descripción", 0.0, R.drawable.placeholder)
            }
        } else {
            // Si no hay argumentos, usamos un producto por defecto
            producto = Producto(0, "Sin nombre", "Sin descripción", 0.0, R.drawable.placeholder)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_producto, container, false)

        val imagenProducto: ImageView = view.findViewById(R.id.detalle_imagen_producto)
        val nombreProducto: TextView = view.findViewById(R.id.detalle_nombre_producto)
        val descripcionProducto: TextView = view.findViewById(R.id.detalle_descripcion_producto)
        val precioProducto: TextView = view.findViewById(R.id.detalle_precio_producto)
        val botonComprar: Button = view.findViewById(R.id.boton_comprar)


        imagenProducto.setImageResource(producto.imagenResourceId)
        nombreProducto.text = producto.nombre
        descripcionProducto.text = producto.descripcion
        precioProducto.text = "$${producto.precio}"

        botonComprar.setOnClickListener {
            val sharedPrefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            val gson = Gson()

            val jsonActual = sharedPrefs.getString("productos", null)
            val type = object : com.google.gson.reflect.TypeToken<MutableList<Producto>>() {}.type
            val productosActuales = if (!jsonActual.isNullOrEmpty()) {
                gson.fromJson<MutableList<Producto>>(jsonActual, type)
            } else {
                mutableListOf()
            }


            if (!productosActuales.any { it.id == producto.id }) {
                productosActuales.add(producto)

                val nuevoJson = gson.toJson(productosActuales)
                editor.putString("productos", nuevoJson)
                editor.apply()

                Toast.makeText(requireContext(), "✅ ${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "⚠️ Este producto ya está en el carrito", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }
}