package com.fiqsky.latihansoall

/**
author Fiqih
Copyright 2023, FiqSky Project
 **/
data class Soal(
    val id: String = "",
    val kategori: String = "",
    val pertanyaan: String = "",
    val jawaban: Map<String, String> = mapOf(),
    val jawaban_benar: String = ""
)