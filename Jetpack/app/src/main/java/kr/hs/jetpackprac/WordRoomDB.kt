package kr.hs.jetpackprac

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
// Room 데이터베이스
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDB : RoomDatabase() {

    abstract fun wordDao(): WordDAO

    companion object {
        // Singleton : 여러 개의 DB 인스턴스가 동시에 열리는 것을 방지한다

        @Volatile
        private var INSTANCE: WordRoomDB? = null

        // Singleton을 반환
        fun getDatabase(context: Context): WordRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                // databaseBuilder를 사용하여
                // applicationContext에서 객체를 만들고 이름을 "word_database"로 지정한다
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}