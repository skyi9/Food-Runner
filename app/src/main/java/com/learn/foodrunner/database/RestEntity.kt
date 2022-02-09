package com.learn.foodrunner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurants")
data class RestEntity(
    @PrimaryKey val res_id : Int,

    @ColumnInfo(name = "res_name") val resName : String,
    @ColumnInfo(name = "res_rating") val resRating : String,
    @ColumnInfo(name = "res_price") val resPrice : String,
    @ColumnInfo(name = "res_imgUrl") val resImgUrl : String

    )
