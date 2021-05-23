package kr.hs.jetpackprac

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// 이 프로젝트에서는
// DB instance와 저장소 instance를 하나씩만 사용한다
// 이 작업을 쉽게 실행하기 위해 instance를 Application클래스의 멤버로 생성한다
// 그럼 매번 구성하지 않고 매번 필요할 때마다 Application에서 가져올 수 있다

// TODO 01. Application 클래스 생성
class WordsApplication : Application() {
    // DB를 채우기 위해, applicationScope로 DB instance 생성
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // by lazy . 키워드를 사용하여
    // 앱이 시작할 때 생성되는 것이 아니라, 그들이 필요할 때 생성되게 한다

    // DB instance 생성
    val database by lazy { WordRoomDB.getDatabase(this, applicationScope) }
    // DB DAO에 기반하여 저장소 instance 생성
    val repository by lazy { WordRepository(database.wordDao()) }
}

// TODO 02. AndroidManifest파일 설정 업데이트
// <application>를 android:name=".WordsApplication"로 설정
