# JSON 파일 데이터를 Kotlin object로 변환하기

[Kotlin Android - Read JSON file from assets using Gson - BezKoder](https://bezkoder.com/kotlin-android-read-json-file-assets-gson/)

---

# Q. 어디에 assets 폴더와 JSON 파일을 위치시키나?

우선 assets 폴더를 추가해주어야 한다. app/src/main에 assets 폴더를 생성한다.

이 폴더는 java와 res폴더 옆에 있어야 한다.

JSON 파일은 assets 폴더 내에 있는지 확인한다.

# Data Class 정의

가져올 json파일 데이터의 형식을 data class 키워드를 통해 정의한다

Kotlin 코드는 다음과 같다

`data class Person (`

   `val name: String,`

   `val age: Int,`

   `val message: List`

`)`

# assets 폴더내 JSON 파일을 읽어오는 메소드 생성

`fun getJsonDataFromAsset(context: Context, fileName: String): String? {`

   `val jsonString: String`

   `try {`

      `jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }`

   `} catch (ioException: IOException) {`

      `ioException.printStackTrace()
return null`

   `}
return jsonString`

`}`

위 메소드는 2개의 매개변수를 가지고 있다.

AssetManager 객체는 context.assets 으로 부터 얻을 수 있다. AssetManager.open() 메소드를 통해 assets 폴더 안의 파일을 열 수 있다. 이 메소드는 ACCESS_STREAMING 모드를 사용하며, InputStream을 반환한다.

bufferedReader() 메소드는 InputStream을 읽을 객체를 생성한다. 그리고 readText() 메소드는 위 객체를 String 형으로 읽을 수 있도록 해준다.

# Gson 패키지로 JSON 파일 변환

com.google.gson.Gson 패키지는 JSON 파일을 해석하기 위해 fromJson() 메소드를 제공한다. fromJson() 메소드의 프로토타입은 다음과 같다.

```kotlin
public <T> T fromJson​(json, classOfT) throws JsonSyntaxException
public <T> T fromJson​(json, typeOfT) throws JsonSyntaxException
```

T : 원하는 객체 자료형

json : JsonElement 객체거나, Reader 객체, String

classOfT : T의 클래스

typeOfT : 특정하게 생성된 타입

# Android Project에 Gson 추가

build.gradle 파일에서 다음을 추가한다

`dependencies {`

   `implementation 'com.google.code.gson:gson:2.8.5'`

`}`

# JSON String을 Kotlin object로 변환하는 메소드

`fun getObjectListFrom(context: Context, fileName: String): List {`

   `val jsonFileString = getJsonDataFromAsset(context, fileName) Log.i("data", jsonFileString.toString())`

   `val gson = Gson()`

   `val listPersonType = object : TypeToken>() {}.type`

   `var persons: List = gson.fromJson(jsonFileString, listPersonType) persons.forEachIndexed { idx, person ->`

   `Log.i("data", "> Item $idx: $person")
}`

   `return persons`

`}`

위 코드를 보면, Gson.fromJson() 를 통해 JSON String을 List<Person>으로 변환하였다.

추가적으로 Array나 Map 형으로 JSON 데이터를 가져오는 방법은 아래 웹사이트가 있다.

[Kotlin - Convert object to/from JSON string using Gson - BezKoder](https://bezkoder.com/kotlin-parse-json-gson/)

