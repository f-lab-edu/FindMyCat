package com.flab.findmycat.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flab.findmycat.data.database.dao.RepoAndOwnerDao
import com.flab.findmycat.data.database.model.Owner
import com.flab.findmycat.data.database.model.Repo

@Database(
    entities = [Repo::class, Owner::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepoAndOwnerDao(): RepoAndOwnerDao

    companion object {

        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var instance: AppDatabase? = null

        // If the instance is not null, then return it, if it is, then build the database
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        // Function that builds the database
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()
    }
}
