package com.rndns.timeredt

import android.content.Context
import androidx.room.*
import java.sql.Date

@Entity(tableName = "TimerObj")
data class TimerObj(
    @PrimaryKey var startat: String,
    @ColumnInfo(name = "endat") var endat: String,
    @ColumnInfo(name = "donetask") var donetask: String?,
    @ColumnInfo(name = "period") var period: String?
)

@Dao
interface TimerObjDao {
    @Query("SELECT * FROM TimerObj")
    fun getAll(): List<TimerObj>
    @Query("SELECT startat FROM TimerObj")
    fun getStartat(): List<String>
    @Insert
    fun insert(timerobj: TimerObj)
    @Update
    fun update(timerobj: TimerObj)
    @Delete
    fun delete(timerobj: TimerObj)
}

// Database
@Database(entities = arrayOf(TimerObj::class), version = 1)
abstract class TimerDatabase: RoomDatabase() {
    abstract fun TimerObjDao(): TimerObjDao

    companion object {
        private var INSTANCE: TimerDatabase? = null

        fun getInstance(context: Context): TimerDatabase? {
            if (INSTANCE == null) {
                synchronized(TimerDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TimerDatabase::class.java, "rndns_timer.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}