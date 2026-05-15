package com.example.grameenlight.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complaints")
data class ComplaintEntity(

    @PrimaryKey
    val id: String = "",

    val pole: String = "",

    val issue: String = "",

    var status: String = "",

    val date: String = "",

    val note: String = ""
)
