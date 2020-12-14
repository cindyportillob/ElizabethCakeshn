package com.example.elizabethcakeshn

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Users (

    val id: String="",
    val Nombre: String = "",
    val Email: String = "",
    val image: String="",
    val mobile: Long = 0,
    val genero: String="",
    val Pcompleto: Int = 0): Parcelable

