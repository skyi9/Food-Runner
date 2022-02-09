package com.learn.foodrunner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResDao {

    @Insert
    fun insertRes(restEntities: RestEntity)

    @Delete
    fun deleteRes(restEntities: RestEntity)

    @Query("SELECT * FROM restaurants")
    fun getAllRes() : List<RestEntity>

    @Query("SELECT * FROM restaurants WHERE res_id = :resId")
    fun getResById(resId : String) : RestEntity

}