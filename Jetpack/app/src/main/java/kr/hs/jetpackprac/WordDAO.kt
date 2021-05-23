package kr.hs.jetpackprac

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// TODO 02. DAO(데이터 액세스 객체) 정의
//  (SQL쿼리를 지정하여 메소드호출과 연결)(interface나 추상클래스)

// Room의 DAO 클래스
@Dao
interface WordDAO {

    // insert 쿼리 (+ @Delete, @Update)
    // onConflict 속성: 충돌전략 설정
    //     OnConflictStrategy.IGNORE: 이미 목록에 있는 단어와 정확하게 같다면, 새 단어를 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

//    @Query("SELECT * FROM word_table ORDER BY word ASC")
//    fun getAlphabetizedWords(): List<Word>

    // 데이터 변경사항을 관찰하려면, kotlinx-coroutines의 Flow를 사용
    // Flow 형의 반환값을 사용하면, Room은 DB가 업데이트될 때 Flow 업데이트에 필요한 코드 생성
    // Flow= 값의 비동기 시퀀스. 비동기작업에서 값을 생성할 수 있는 값을 한 번에 하나씩 생성
    // 이 프로젝트에서는 Flow를 ViewModel의 ViewData로 변환
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}



