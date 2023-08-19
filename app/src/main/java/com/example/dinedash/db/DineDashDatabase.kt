package com.example.dinedash.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dinedash.models.Product


@Database(entities = [Product::class], version = 4, exportSchema = false)
abstract class DineDashDatabase: RoomDatabase() {
    abstract fun getDao(): DineDashDao

    companion object{
        @Volatile
        private var INSTANCE : DineDashDatabase? = null

        fun getDatabase(context: Context): DineDashDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DineDashDatabase::class.java,
                    "dineDash_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                return instance
            }
        }
    }
}