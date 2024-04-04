package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import android.util.Log
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.extensions.showStyledSnackBar
import com.kuldeep.clubquiz.obj.Question
import com.kuldeep.clubquiz.util.ApiInstance
import com.kuldeep.clubquiz.util.BundleArguments
import com.kuldeep.clubquiz.util.PreferenceHelper

class OnlineQuizFragment : QuizFragment() {
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.getString(BundleArguments.category)
    }

    override suspend fun fetchQuestions(): List<Question> {
        return try {
            ApiInstance.apiHelper.getQuestions(category)
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, e.toString())
            binding.root.showStyledSnackBar(R.string.network_error)
            listOf()
        }
    }

    override suspend fun prepareNextQuestions(): Boolean {
        if (questions.size > questionIndex + 1) return true

        if (PreferenceHelper.isUnlimitedMode()) {
            val newQuestions = fetchQuestions()
            questions += newQuestions
            return newQuestions.isNotEmpty()
        }

        return false
    }
}
