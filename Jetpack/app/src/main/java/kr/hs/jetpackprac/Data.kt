package kr.hs.jetpackprac

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Data {

}

// TODO 01. Word 데이터 클래스 정의

// way 1.
//data class Word(val word: String)

// way 2.
//@Entity(tableName = "word_table")
//class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

// way 3.
//SQLite 테이블(테이블명 = "word_table)
@Entity(tableName = "word_table")
class Word(
    // 기본키
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word") val word: String
)

