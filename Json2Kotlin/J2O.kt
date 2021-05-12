package mac.stranger.helloworld

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class J2O {
    // Json 파일 데이터를 Object(class)로 가져오기 [kotlin]
    // 참고: https://bezkoder.com/kotlin-android-read-json-file-assets-gson/

    data class Person (
            val name: String,
            val age: Int,
            val message: List<String>
    )

    // Asset 폴더에서 fileName 파일 읽기
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getObjectListFrom(context: Context, fileName: String): List<Person> {
        val jsonFileString = getJsonDataFromAsset(context, fileName)
        Log.i("data", jsonFileString.toString())

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Person>>() {}.type

        var persons: List<Person> = gson.fromJson(jsonFileString, listPersonType)
        persons.forEachIndexed { idx, person ->
            Log.i("data", "> Item $idx:\n$person")
        }
        return persons
    }
}