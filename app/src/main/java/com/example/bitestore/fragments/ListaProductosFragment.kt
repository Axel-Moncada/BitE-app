package com.example.bitestore.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitestore.Adapters.ProductoAdapter
import com.example.bitestore.R
import com.example.bitestore.Models.Producto

class ListaProductosFragment : Fragment ()  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val listaProductos = mutableListOf<Producto>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_productos, container, false)

        Log.d("ListaProductosFragment", "✅ Fragment inflado correctamente")
        recyclerView = view.findViewById(R.id.recycler_productos)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }
        recyclerView.layoutManager = layoutManager


        cargarProductos()

        adapter = ProductoAdapter(listaProductos) { producto ->

            val detalleFragment = DetalleProductoFragment.newInstance(producto)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detalleFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        return view
    }

    private fun cargarProductos() {
        listaProductos.add(
            Producto(
                1,
                "Monitor Samsung 24\"",
                "Monitor FHD 24 pulgadas con tecnología IPS",
                499000.0,
                R.drawable.monitor
            )
        )

        listaProductos.add(
            Producto(
                2,
                "Laptop Lenovo i5",
                "Laptop 15.6'' con Intel Core i5, 8GB RAM y SSD 256GB",
                2250000.0,
                R.drawable.laptop
            )
        )

        listaProductos.add(
            Producto(
                3,
                "Auriculares Bluetooth",
                "Auriculares inalámbricos con cancelación de ruido",
                189000.0,
                R.drawable.auriculares
            )
        )

        listaProductos.add(
            Producto(
                4,
                "Smartwatch Huawei",
                "Reloj inteligente con medición de frecuencia cardíaca",
                399000.0,
                R.drawable.smartwatch
            )
        )

        listaProductos.add(
            Producto(
                5,
                "Tablet Samsung Galaxy",
                "Tablet 10.4'' con Android 13 y 64GB almacenamiento",
                899000.0,
                R.drawable.tablet
            )
        )

        listaProductos.add(
            Producto(
                6,
                "Mouse Gamer RGB",
                "Mouse óptico con sensor de alta precisión y luces LED",
                79000.0,
                R.drawable.mouse
            )
        )
        Log.d("ListaProductosFragment", "🛒 Productos cargados: ${listaProductos.size}")
    }


}