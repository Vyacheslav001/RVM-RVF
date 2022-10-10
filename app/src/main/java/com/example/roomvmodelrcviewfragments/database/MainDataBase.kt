package com.example.roomvmodelrcviewfragments.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomvmodelrcviewfragments.Product

@Database(entities = [Product::class], version = 1)
abstract class MainDataBase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        fun getDataBase(context: Context): MainDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDataBase::class.java,
                "base.db"
            ).build()
        }
    }
}