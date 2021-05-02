# Intent로 데이터 넘겨주기 [Kotlin]

---

## Q. ‘ Intent 인텐트’ 란?

[Intent (Android) - Wikipedia](https://en.wikipedia.org/wiki/Intent_(Android))

> An **Intent** in the [Android operating system](https://en.wikipedia.org/wiki/Android_(operating_system)) is a [software](https://en.wikipedia.org/wiki/Software) mechanism that allows [users](https://en.wikipedia.org/wiki/End-user_(computer_science)) to coordinate the functions of different activities to achieve a task. An Intent is a messaging object[[1]](https://en.wikipedia.org/wiki/Intent_(Android)#cite_note-1) which provides a facility for performing [late runtime binding](https://en.wikipedia.org/wiki/Late_binding) between the code in different applications

Intent는 서로 다른 액티비티 간의 메시지를 주고받기 위한 객체다.

---

## 데이터를 보내는 방법

`val intent = Intent(this, 받는클래스명::class.java)`

`intent.putExtra("변수명”, 보낼데이터)`

`startActivity(intent)`

---

## 데이터를 받는 방법

Kotlin 으로 intent를 입력하면 자동으로 getIntent()를 호출한다.

따라서 intent. 이후에 받을 데이터타입(자료형)에 따라 메소드를 지정해서 데이터를 받는다.

바로 데이터를 받아도 되지만 안전하게 hasExtra()로 데이터가 있는지 검사하였다.

아래는 ArrayList<Object> 데이터를 받기 위한 코드다.

`if (intent.hasExtra("변수명”)) {`

   `저장할변수 = intent.getSerializableExtra(“변수명”) as ArrayList?`

`} else {`

   `Toast.makeText(this, "에러 메시지”, Toast.LENGTH_SHORT)`

`}`

---

## ArrayList<Object> 데이터를 받는 방법은 다음과 같다.

1. intent.getParcelableArrayListExtra(“변수명”)
2. intent.getSerializableExtra(“변수명”)

1 번째 방법을 사용할 경우,

Object에 해당하는 클래스가 Parcelable을 상속해야 한다.

이 Parcelable을 상속하면, 반드시 아래 메소드들을 작성해야하는 부담이 있다.

![Image.png](https://res.craft.do/user/full/fa176664-3985-eb36-93ba-d918d8fd4d32/doc/71717B27-8A01-4B52-A2A9-862A83DB477B/124D081B-0229-4E59-909B-57A3FD968B21_2/Image.png)

[[Android][Kotlin] PutExtra 데이터 전달](https://blog.yena.io/studynote/2017/11/28/Android-Kotlin-putExtra.html)

그에 비해 2 번째 방법을 사용할 경우,

별다른 조치없이 as 키워드를 통해 강제 형변환으로 쉽게 데이터를 받을 수 있다.

또 구글 검색결과 속도가 더 빠르다고 한다.

![Image.png](https://res.craft.do/user/full/fa176664-3985-eb36-93ba-d918d8fd4d32/doc/71717B27-8A01-4B52-A2A9-862A83DB477B/FB7DFFEC-469F-4A55-9ED7-C9DF30FF66C8_2/Image.png)

