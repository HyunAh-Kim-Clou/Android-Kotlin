package kr.hs.jetpackprac

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
// Room 데이터베이스
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDB : RoomDatabase() {

    abstract fun wordDao(): WordDAO

    // DB에 데이터 추가하는 방법
    // 1. DB 만들 때 데이터 추가
    // 2. 데이터를 추가하는 Activity 추가

    // 이 프로젝트에서는 방법 1을 선택하였다

    // TODO 01. RoomDatabase.Callback 생성 후, onCreate() 재정의
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // 데이터 추가
                    populateDatabase(database.wordDao())
                }
            }
        }

        // TODO 04. 초기화할 DB 데이터 작성
        // 앱을 만들 때마다 모두 데이터 삭제 후, 데이터 추가
        suspend fun populateDatabase(wordDao: WordDAO) {
            // Delete all content here.
            wordDao.deleteAll()

            // Word("내용")으로 작성하면 컴파일 오류!!
            // 그래서 Word(0, "내용")로 변경하였다
            // Add sample words.
            var word = Word("Hello")
            wordDao.insert(word)

            word = Word("World!")
            wordDao.insert(word)

            // TODO: Add your own words!
        }
    }

    // TODO 03. applicationScope로 DB instance 생성
    // DB를 채우는 작업은 UI LifeCycle과 관련이 없으므로 코루틴을 사용해서는 안된다
    // 이는 Application LifeCycle와 관련이 있다
    // 따라서 WordsApplication에서 applicationScope를 포함시키고, WordRoomDB.getDatabase로 전달한다

    companion object {
        // Singleton : 여러 개의 DB 인스턴스가 동시에 열리는 것을 방지한다

        @Volatile
        private var INSTANCE: WordRoomDB? = null

        // Singleton을 반환
//        fun getDatabase(context: Context): WordRoomDB {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//
//                // databaseBuilder를 사용하여
//                // applicationContext에서 객체를 만들고 이름을 "word_database"로 지정한다
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WordRoomDB::class.java,
//                    "word_database"
//                ).build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }

        // TODO 02. CoroutineScope를 매개변수로 가져오기 위해 getDatabase() 업데이트
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                // TODO 05. DB build 시퀀스에 콜백 추가
                //  DB build 호출 전에 .addCallback(WordDatabaseCallback(scope))추가

                // databaseBuilder를 사용하여
                // applicationContext에서 객체를 만들고 이름을 "word_database"로 지정한다
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDB::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}