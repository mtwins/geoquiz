package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
 private const val TAG="QUIZVIEMODEL"
class QuizViewModel : ViewModel() {
    var isCheater=false

    private val questionBank= listOf(
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_austrailia, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_ocean, true),
        Question(R.string.question, false)
    )
    private var currentIndex=0

    val currentAsuner: Boolean
    get() = questionBank[currentIndex].answer
    val currentText: Int
    get() = questionBank[currentIndex].textResId
    fun moveToNext(){
        currentIndex= (currentIndex+1)%questionBank.size
    }
    init {
        Log.d(TAG, "onitit")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCLeaered")
    }
}