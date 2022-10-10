package com.example.roomvmodelrcviewfragments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey()
    val number: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "Price")
    val price: String
)
