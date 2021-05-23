package kr.hs.jetpackprac

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// 저장소 클래스: 여러 데이터 자원 접근(DAO와 NW간의 접근)을 추상화하기 위해 존재
//     쿼리를 관리하고, 여러 Backend를 사용하도록 허용

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDAO) {

    // Room은 모든 쿼리를 각각 하나의 Thread에서 실행하도록 한다
    // Observed Flow will notify the observer when the data has changed.
    // 관찰된 Flow는 특정 Observer에게 '그 데이터가 변화했는지' 알린다?
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    // suspend: 코루틴, 다른 suspend 함수에서 이를 호출해야한다고 compiler에게 알린다
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}