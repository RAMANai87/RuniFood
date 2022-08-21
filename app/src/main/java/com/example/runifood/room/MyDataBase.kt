package com.example.runifood.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class MyDataBase :RoomDatabase(){

    abstract val foodDao: InterFaceDao

    companion object {
        private var database: MyDataBase? = null
        fun getDataBase(context: Context) :MyDataBase{

            if (database == null) {
                database = Room.databaseBuilder(context.applicationContext,
                MyDataBase::class.java, "FoodDataBase.db")
                    .allowMainThreadQueries()
                    .build()
            }

            return database!!
        }
    }

}