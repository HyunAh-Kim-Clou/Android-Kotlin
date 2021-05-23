package kr.hs.jetpackprac

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// TODO 01. Word 데이터 클래스 정의

// way 1.
//data class Word(val word: String)

// way 2.
@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

// way 3. error 발생! (auto생성이 잘 안됨)
//SQLite 테이블(테이블명 = "word_table)
//@Entity(tableName = "word_table")
//class Word(
//    `@PrimaryKey(autoGenerate = true) val id: Int,`
//    `@ColumnInfo(name = "word") val word: String`
//)

