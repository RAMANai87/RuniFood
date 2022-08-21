package com.example.runifood.room

import androidx.room.*

@Dao
interface InterFaceDao {

    @Insert
    fun insertFood(food: Food)

    @Insert
    fun insertAllFood(food: ArrayList<Food>)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM TABLE_FOOD")
    fun deleteAllFoods()

    @Query("SELECT * FROM Table_Food")
    fun getAllFoods() :List<Food>

    @Query("SELECT * FROM Table_Food WHERE txtSubject LIKE '%' ||:searching || '%'")
    fun searchFoods( searching: String ) :List<Food>

}