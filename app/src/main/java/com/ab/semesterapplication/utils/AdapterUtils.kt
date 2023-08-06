package com.ab.semesterapplication.utils

import androidx.core.content.res.ResourcesCompat
import com.ab.semesterapplication.R
import com.ab.semesterapplication.databinding.HistoryListViewBinding
import com.ab.semesterapplication.databinding.SemesterExamListViewBinding
import com.ab.semesterapplication.databinding.SubjectListViewBinding
import com.ab.semesterapplication.model.Exams
import com.ab.semesterapplication.model.Subject

object AdapterUtils {

    fun setUpSubjectAdapter(subjectList : List<Subject>) : GenericAdapter<Subject, SubjectListViewBinding,List<Subject>>{

        val adapter = GenericAdapter(R.layout.subject_list_view,object : GenericAdapterInteraction<Subject, SubjectListViewBinding,List<Subject>>(){

            override fun bindingViewHolder(binding: SubjectListViewBinding, data: Subject,
                holder: GenericAdapter.GenericViewHolder<Subject, SubjectListViewBinding, List<Subject>>, additionalData: List<Subject>?) {
                binding.subject.text = data.subjectName
                binding.subjectImage.background = ResourcesCompat.getDrawable(binding.root.resources,data.resourceId,null)
            }

            override fun onClicked(data: Subject,binding: SubjectListViewBinding) {
                //override fun not implemented
            }

        })
        adapter.addItems(subjectList)

        return adapter
    }

    fun setTakeExamsAdapter(exams : List<Exams>,delegate: ExamsDelegate) : GenericAdapter<Exams, SemesterExamListViewBinding,List<Unit>>{
        val adapter = GenericAdapter(R.layout.semester_exam_list_view,object : GenericAdapterInteraction<Exams,SemesterExamListViewBinding,List<Unit>>(){
            override fun bindingViewHolder(binding: SemesterExamListViewBinding, data: Exams,
                holder: GenericAdapter.GenericViewHolder<Exams, SemesterExamListViewBinding, List<Unit>>,
                additionalData: List<Unit>?
            ) {
                binding.semesterName.text = data.semester
                binding.time.text = binding.root.resources.getString(R.string.exam_time)
            }

            override fun onClicked(data: Exams, binding: SemesterExamListViewBinding) {
                delegate.takeExam(data)
            }
        })

        adapter.addItems(exams)
        return adapter
    }


    fun setExamHistoryListAdapter(exams : List<Exams>) : GenericAdapter<Exams, HistoryListViewBinding,List<Unit>>{
        val adapter = GenericAdapter(R.layout.history_list_view,object : GenericAdapterInteraction<Exams,HistoryListViewBinding,List<Unit>>(){
            override fun bindingViewHolder(
                binding: HistoryListViewBinding, data: Exams,
                holder: GenericAdapter.GenericViewHolder<Exams, HistoryListViewBinding, List<Unit>>, additionalData: List<Unit>?
            ) {
                val resources = binding.root.resources

                binding.semesterName.text = data.semester
                binding.semesterImage.background = ResourcesCompat.getDrawable(binding.root.resources,data.icons,null)
                binding.score.text = String.format(resources.getString(R.string.score_out_of_total),data.score,data.totalScore)
            }

            override fun onClicked(data: Exams, binding: HistoryListViewBinding) {
                //override fun not implemented
            }
        })

        adapter.addItems(exams)
        return adapter
    }


}


interface ExamsDelegate {
    fun takeExam(exam : Exams)
}