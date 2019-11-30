package com.constancio.data.local.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.constancio.data.local.db.code.CodeDao
import com.constancio.data.local.db.code.PathEntity
import com.constancio.data.local.db.code.ResponseCodeEntity

@Database(entities = [PathEntity::class, ResponseCodeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(application: Application, databaseName: String): AppDatabase =
            Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                databaseName
            ).allowMainThreadQueries().build()

    }

    abstract fun codeDao(): CodeDao
}