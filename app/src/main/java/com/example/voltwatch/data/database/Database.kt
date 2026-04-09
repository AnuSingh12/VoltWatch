package com.example.voltwatch.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.voltwatch.data.database.history.BatteryHistoryData
import com.example.voltwatch.data.database.history.HistoryDao

@Database(
    entities = [
        BatteryHistoryData::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "volt_watch_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
