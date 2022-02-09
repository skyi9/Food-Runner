package com.learn.foodrunner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RestEntity::class] , version = 1)
abstract class ResDatabase : RoomDatabase() {

    abstract fun resDao() : ResDao

}