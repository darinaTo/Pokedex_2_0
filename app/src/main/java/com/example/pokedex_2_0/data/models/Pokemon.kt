package com.example.pokedex_2_0.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Pokemon  constructor (
    @PrimaryKey
    val image : String,
    val name : String
)


