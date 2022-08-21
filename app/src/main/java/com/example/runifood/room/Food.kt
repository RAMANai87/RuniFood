package com.example.runifood.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Table_Food")
data class Food(
    @PrimaryKey
    val id: Int? = null,
    val txtSubject: String,
    val txtDistance: String,
    val txtPrice: String,
    val FoodLocation: String,
//    @ColumnInfo(name = "urlPic")
    val urlImage: String,
    val vote: Int,
    val rating: Float
)


