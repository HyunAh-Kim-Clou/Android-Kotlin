package kr.hs.jetpackprac

import androidx.lifecycle.*
import kotlinx.coroutines.launch

// ViewModel: 저장소와 UI 간의 통신센터
//     UI에 데이터를 제공, 데이터 구성 변경에도 유지된다
//     Fragment 간의 데이터를 공유한다
//     LifeCycle Library의 일부

// LiveData: observable(관찰가능한) 데이터 holder
//     데이터가 변경될 때마다 알림을 받을 수 있다
//     Flow와 달리 LifeCycle을 인식한다

// 이 프로젝트에서
// ViewModel은 저장소 데이터를 Flow에서 LiveData로 변환하고,
// LiveData로 UI에 보여준다
// 이렇게 하면 DB 데이터 변경될 때마다 UI가 자동으로 업데이트 된다

// Kotlin의 코루틴은 CoroutineScope 내에서 실행된다
// 이는 전체작업에 걸쳐 코루틴의 전체시간을 제어한다
// 만약 CoroutineScope의 작업을 취소하면, 해당 범위에서 시작된 코루틴은 모두 취소된다

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // asLiveData(): Flow 형을 LiveData 형으로 변환
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    // UI에서 저장소의 insert메소드 캡슐화
    // ViewModel에는 viewModelScope라는 CoroutineScope(코루틴 단위)가 있다
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

// Framework에서 ViewModel의 LifeCycle을 처리
// ViewModel생성 시, LifeCycle이 짧은 Context(ex. Activity, Fragment, View)로 참조하지 마시오
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
