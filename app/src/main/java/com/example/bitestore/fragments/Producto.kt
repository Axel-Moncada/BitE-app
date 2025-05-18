
package com.example.bitestore.Models

import android.os.Parcel
import android.os.Parcelable


data class  Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenResourceId: Int

) : Parcelable {
    constructor(percel: Parcel) : this (
        percel.readInt(),
        percel.readString() ?: "",
        percel.readString() ?: "",
        percel.readDouble(),
        percel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeInt(imagenResourceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }

}