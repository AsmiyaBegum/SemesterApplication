package com.ab.semesterapplication

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ab.semesterapplication.databinding.FragmentSemesterQuestionBinding
import com.ab.semesterapplication.model.Constants
import com.ab.semesterapplication.model.Questions
import com.ab.semesterapplication.utils.Utils.showVisibility
import com.jakewharton.rxbinding.view.clicks
import rx.android.schedulers.AndroidSchedulers
import java.util.*

/**
 * Fragment to display a list of questions and final result screen
 */
class SemesterQuestionFragment : Fragment() {

    lateinit var binding: FragmentSemesterQuestionBinding
    private var countDownTimer : CountDownTimer? = null
    private var currentQnIndex = 1
    private var right = 0
    private var selectedAnswer : String = ""
    private var semesterName : String = ""

    private val questionList = listOf(
        Questions(1,"Android is based on which kernel?", arrayOf("Linux","Windows","Mac","Others"),"Linux"),
        Questions(2,"Which media format is not supported by android?", arrayOf("MP4","AVI","MIDI","MPEG"),"AVI"),
        Questions(3,"What is JNI in android ?", arrayOf("Java Interface","Java Native Interface","Java Network Interface","None of the above"),"Java Native Interface"),
        Questions(4,"option 1 is a answer", arrayOf("1","2","3","4"),"1"),
        Questions(5,"option 2 is a answer", arrayOf("1","2","3","4"),"2")
    )


    private fun bind(){

        binding.next.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleNextButtonEvent()
            }


        binding.finish.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                resetCurrentIndexIfNotSelected()
                showQnScreen(false)
            }

        binding.backToHome.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                findNavController().navigate(R.id.action_SemesterQnFragment_to_SemesterHomeFragment)
            }

        binding.optionList.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswer = binding.optionList.findViewById<RadioButton>(checkedId)?.text?.toString()?:""
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        semesterName = requireArguments().getString(Constants.EXAM_NAME,"")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSemesterQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showQnScreen(true)
        initCountDownTimer()
        bind()
    }

    private fun updateQnList(){
        binding.questionNo.text = String.format(getString(R.string.question_no),currentQnIndex,questionList.size)
        val currentQuestion = questionList[currentQnIndex-1]
        binding.question.text = String.format(getString(R.string.question),currentQuestion.id,currentQuestion.question)
        val optionList = currentQuestion.options
        binding.option1.text = optionList[0]
        binding.option2.text = optionList[1]
        binding.option3.text = optionList[2]
        binding.option4.text = optionList[3]

    }

    private fun showQnScreen(show : Boolean){
        binding.questionScreen.showVisibility(show)
        binding.finishLayout.showVisibility(!show)
        if(show){
            updateQnList()
        }else{
            binding.rightCount.text = right.toString()
            binding.wrongCount.text = (currentQnIndex - right).toString()
            binding.skipCount.text = (questionList.size - currentQnIndex).toString()
            binding.userName.text = Constants.USER_NAME
            binding.score.text = String.format(getString(R.string.score),right,questionList.size)
            binding.scoreOutOfTotal.text = String.format(getString(R.string.score_out_of_total),right,questionList.size)
            binding.semester.text = semesterName
        }

    }

    private fun initCountDownTimer() {
        countDownTimer = object : CountDownTimer(Constants.DEFAULT_EXAM_TIMER, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update UI with the remaining time
                val secondsRemaining = millisUntilFinished / 1000
                updateUI(secondsRemaining)
            }

            override fun onFinish() {
                 //override fun  not implemented
            }
        }

        countDownTimer?.start()
    }

    private fun handleNextButtonEvent(){
        when {
            currentQnIndex == questionList.size -> {
                showQnScreen(false)
            }
            selectedAnswer.isBlank() -> {
                Toast.makeText(requireContext(),getString(R.string.select_any_answer),Toast.LENGTH_SHORT).show()
            }
            else -> {
                if(selectedAnswer == questionList[currentQnIndex-1].answer) right++
                selectedAnswer = ""
                currentQnIndex++
                updateQnList()
            }
        }
        binding.optionList.clearCheck()
    }


    private fun updateUI(secondsRemaining: Long) {
        if(secondsRemaining != 0L){
            val minutes = secondsRemaining / 60
            val seconds = secondsRemaining % 60
            val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            binding.examTimer.text = formattedTime
        }else{
           resetCurrentIndexIfNotSelected()
            showQnScreen(false)
        }
    }

    private fun resetCurrentIndexIfNotSelected(){
        if(binding.optionList.checkedRadioButtonId == -1) {
            currentQnIndex--
        }
        countDownTimer?.cancel()
    }
}