package com.laufitness

data class Usuarios(
    val id: String,
    val nombre: String,
    val correo: String,
    val fechaNacimiento: String,
    val fechaRegistro: String,
    val membresiaId: Int,
    val membresia: String,
    val entrenador: String
)
