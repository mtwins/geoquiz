package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG="Main Activity"
private const val REQUEST_CODE=0
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private  lateinit var falseButton: Button
    private  lateinit var nextButton: Button
    private  lateinit var prevButton: Button
    private  lateinit var cheatButton: Button
    private  lateinit var questionView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    private val questionBank= listOf(
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_austrailia, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_ocean, true),
        Question(R.string.question, false)
    )
    private var currentIndex=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG," ON Create bundle called")
        setContentView(R.layout.activity_main)
        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton=findViewById(R.id.next_button)
        questionView=findViewById(R.id.question_view_text)
        prevButton=findViewById(R.id.prev_button)
        cheatButton=findViewById(R.id.cheat_button)
        cheatButton.setOnClickListener {
            startActivityForResult(CheatActivity.newIntent(this, quizViewModel.currentAsuner), REQUEST_CODE)
        }
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
//            val makeText = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
//            makeText.setGravity(Gravity.TOP or Gravity.LEFT,0,0)
//            makeText.show()
            checkAnswer(false)
        }
        updateQuestion()

        questionView.setOnClickListener { view ->
            currentIndex=(currentIndex+1)%questionBank.size
            updateQuestion()
        }
        nextButton.setOnClickListener { v:View->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener { v:View->
            currentIndex=(currentIndex-1)%questionBank.size
            updateQuestion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= Activity.RESULT_OK)
            return
        if(requestCode==REQUEST_CODE)
            quizViewModel.isCheater=data?.getBooleanExtra("ANSWER_SHOWN",false) ?:false
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG," ON Start")
    }
 override fun onStop() {
        super.onStop()
        Log.d(TAG," onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG," onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, " onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun updateQuestion() {
        questionView.setText(quizViewModel.currentText)
    }

    private fun checkAnswer(answer:Boolean){
        val correctAnswer =quizViewModel.currentAsuner
        var message= when{quizViewModel.isCheater-> R.string.judgement_toast
            correctAnswer==answer -> R.string.correct_toast
            else->R.string.incorrect_toast}
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
