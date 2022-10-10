package com.example.roomvmodelrcviewfragments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomvmodelrcviewfragments.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :name")
    fun getProductByName(name: String): Flow<Product>

    @Query("DELETE FROM products")
    fun deleteAllProducts()

    @Query("DELETE FROM products WHERE name LIKE :name")
    fun deleteProduct(name: String)

    @Query("UPDATE products SET name = :newName, price = :newPrice WHERE number LIKE :number")
    fun updateProduct(number: Int, newName: String, newPrice: String)
}