package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.FragmentEditQuizBinding
import com.kuldeep.clubquiz.extensions.navigate
import com.kuldeep.clubquiz.extensions.toHTML
import com.kuldeep.clubquiz.obj.Quiz
import com.kuldeep.clubquiz.util.BundleArguments
import com.kuldeep.clubquiz.util.PreferenceHelper

class EditQuizFragment : Fragment() {
    private lateinit var binding: FragmentEditQuizBinding
    private var quizIndex: Int = -1
    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizIndex = arguments?.getInt(BundleArguments.quizIndex)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditQuizBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quiz = PreferenceHelper.getQuizzes()[quizIndex]

        binding.quizName.text = quiz.name
        binding.questionCount.text =
            context?.getString(R.string.questions, quiz.questions?.size.toString())

        val adapter = ArrayAdapter<Spanned>(requireContext(), R.layout.list_item)

        quiz.questions?.forEach {
            adapter.add(it.question.toHTML())
        }

        binding.questionsList.adapter = adapter
        binding.questionsList.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                val createQuizFragment = CreateQuizFragment()
                val bundle = Bundle()
                bundle.putInt(BundleArguments.quizIndex, quizIndex)
                bundle.putInt(BundleArguments.questionIndex, index)
                createQuizFragment.arguments = bundle
                parentFragmentManager.navigate(createQuizFragment)
            }

        binding.newQuestionFAB.setOnClickListener {
            val createQuizFragment = CreateQuizFragment()
            val bundle = Bundle()
            bundle.putInt(BundleArguments.quizIndex, quizIndex)
            createQuizFragment.arguments = bundle
            parentFragmentManager.navigate(createQuizFragment)
        }
    }
}
