package com.example.grameenlight.data

data class Complaint(

    val id: String,
    val pole: String,
    val issue: String,

    var status: String,

    val date: String,

    val note: String = ""
)