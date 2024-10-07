package com.iacc.manuelroa_20241004

data class Locall(
    val fecha: String,
    val local_id: String,
    val fk_region: String,
    val fk_comuna: String,
    val fk_localidad: String,
    val local_nombre: String,
    val comuna_nombre: String,
    val localidad_nombre: String,
    val local_direccion: String,
    val funcionamiento_hora_apertura: String,
    val funcionamiento_hora_cierre: String,
    val local_telefono: String,
    val local_lat: String,
    val local_lng: String,
    val funcionamiento_dia: String
)

