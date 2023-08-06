package com.ab.semesterapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ab.semesterapplication.databinding.FragmentSemesterHomeBinding
import com.ab.semesterapplication.model.Constants
import com.ab.semesterapplication.model.Exams
import com.ab.semesterapplication.model.Subject
import com.ab.semesterapplication.utils.AdapterUtils
import com.ab.semesterapplication.utils.ExamsDelegate


/**
 * Semester Home fragment to display the list of subjects, exams, history of exam taken
 */
class SemesterHomeFragment : Fragment(), ExamsDelegate {

    lateinit var binding: FragmentSemesterHomeBinding

    private val exams = listOf(
        Exams("3rd semester 2", 2,R.drawable.biology_logo),
        Exams("3rd semester 1", 2,R.drawable.chemisty_logo),
        Exams("3rd semester", 0,R.drawable.english_logo),
        Exams("3rd semester 4", 3,R.drawable.chemisty_logo),
        Exams("3rd semester 3", 2,R.drawable.biology_logo),
        Exams("3rd semester 2", 1,R.drawable.english_logo),
        Exams("3rd semester 3", 4,R.drawable.biology_logo),
        Exams("3rd semester 2", 3,R.drawable.english_logo),
        Exams("3rd semester 4", 2,R.drawable.biology_logo)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentSemesterHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bind the data to the ui
        setUserName()
        updateSubjectList()
        updateExamList()
        updateExamHistoryList()

    }

    private fun setUserName(){
        /*
           To dynamically set user name, before setting , get the user name from shared preferences or where it get stored
         */
        binding.helloUser.text = getString(R.string.hello_user,Constants.USER_NAME)
    }


    private fun updateSubjectList(){
        val subjects = listOf(
            Subject(1,R.drawable.biology_logo,getString(R.string.bangle)),
            Subject(2,R.drawable.english_logo,getString(R.string.english)),
            Subject(3,R.drawable.biology_logo,getString(R.string.computer))
        )

        binding.subjectList.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        binding.subjectList.adapter = AdapterUtils.setUpSubjectAdapter(subjects)
    }

    private fun updateExamList() {
     binding.examSemesterList.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
     binding.examSemesterList.adapter = AdapterUtils.setTakeExamsAdapter(exams,this)
    }

    private fun updateExamHistoryList(){
        binding.examHistoryList.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.examHistoryList.adapter = AdapterUtils.setExamHistoryListAdapter(exams)
    }

    override fun takeExam(exam: Exams) {
        val bundle = bundleOf(Constants.EXAM_NAME to exam.semester)
        findNavController().navigate(R.id.action_SemesterHomeFragment_to_SemesterQnFragment,bundle)
    }
}