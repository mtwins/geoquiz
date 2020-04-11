package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue=false
    private lateinit var answerTextView:TextView
    private lateinit var showAnswerBUtton:Button
companion object{
    val ANSWER_IS_TRUE = "answer_is_true"
    fun newIntent(packageContext: Context, answerIsTrue:Boolean): Intent {
        return Intent(packageContext, CheatActivity::class.java).apply {
             putExtra(ANSWER_IS_TRUE, answerIsTrue)
        }
    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerIsTrue=intent.getBooleanExtra(ANSWER_IS_TRUE, false)
        showAnswerBUtton=findViewById(R.id.show_answer_button)
        answerTextView=findViewById(R.id.answer_text_view)
        showAnswerBUtton.setOnClickListener {
            setAnswerShownResult(true)
           var answerText= when{
                answerIsTrue->R.string.true_button
                else-> R.string.false_button
            }
            answerTextView.setText(answerText)
        }
    }
    private fun setAnswerShownResult(isAnswerShown:Boolean){
        val data=Intent().apply {
            putExtra("ANSWER_SHOWN", isAnswerShown)
        }
        setResult(Activity.RESULT_OK,data)
    }
}
