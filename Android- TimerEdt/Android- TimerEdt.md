# Android- TimerEdt

# Things to Focus on

- Room DB 이용

[Room local DB 공식문서](https://developer.android.com/training/data-storage/room)

[[Android][Kotlin] Room 으로 DB 저장하기](https://blog.yena.io/studynote/2018/09/08/Android-Kotlin-Room.html)

[[안드로이드] ROOM 라이브러리 사용하기 , 코루틴](https://namget.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-ROOM-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-%EC%BD%94%EB%A3%A8%ED%8B%B4)

- Timer 구현 방법 = Thread를 활용하여 구현

```kotlin
Thread(Runnable {
    kotlin.run {
	      while(true) {
	          // execute task
	          Thread.sleep(1000)
	      }
    }
}).start()
```

[Android runOnUiThread Example - Kotlin](https://www.tutorialkart.com/kotlin-android/android-runonuithread-example-kotlin/)

[android - example - kotlin timer task](https://code-examples.net/en/q/295728f)

[timer](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.concurrent/timer.html)

- [Unused] Convert LocalDateTime Format

```kotlin
@RequiresApi(Build.VERSION_CODES.O)
fun convertFormatOfTime(time: String, beforeFormat: String, afterFormat: String): String {
    var bFormat = DateTimeFormatter.ofPattern(beforeFormat)
    var timeobj = bFormat.parse(time) as LocalDateTime
    var aFormat = timeobj.format(DateTimeFormatter.ofPattern(afterFormat))
    return aFormat
}
```

[Kotlin - String을 파싱하여 LocalDate로 변환하는 방법](https://codechacha.com/ko/kotlin-examples-how-to-convert-string-to-localdate/)

- [Undone] ListView.adapter와 List 호환

[Kotlin ListView 구현하기](https://start1a.tistory.com/6)

[](https://developer.android.com/kotlin/first)

---

## Function

시작버튼을 누르면 Timer를 실행하여 현재 시각을 보여준다. 끝버튼을 누르면 Timer가 종료되며 첫 화면으로 돌아간다. 

관련 데이터는 Console창에서 확인할 수 있다. 예를 들면, 버튼 동작 및 DB 저장기록 등.

## Stored Room DB Structure

TimerDatabase

TimerObj

[PK] startat: String

endat: String

donetask: String

period: String

## Source Code

![Android-%20TimerEdt/__2021-02-03_220437.png](Android-%20TimerEdt/__2021-02-03_220437.png)

[activity_main.xml](Android-%20TimerEdt/activity_main.xml)

[activity_timer.xml](Android-%20TimerEdt/activity_timer.xml)

[MainActivity.kt](Android-%20TimerEdt/MainActivity.kt)

[TimerActivity.kt](Android-%20TimerEdt/TimerActivity.kt)

[TimerObj.kt](Android-%20TimerEdt/TimerObj.kt)
