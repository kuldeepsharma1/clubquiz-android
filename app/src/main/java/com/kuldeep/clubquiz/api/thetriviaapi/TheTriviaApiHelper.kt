package com.kuldeep.clubquiz.api.thetriviaapi

import android.util.Log
import com.kuldeep.clubquiz.extensions.formatStats
import com.kuldeep.clubquiz.obj.Category
import com.kuldeep.clubquiz.obj.Question
import com.kuldeep.clubquiz.util.ApiHelper
import com.kuldeep.clubquiz.util.PreferenceHelper
import com.kuldeep.clubquiz.util.RetrofitInstance
import com.fasterxml.jackson.databind.ObjectMapper

class TheTriviaApiHelper : ApiHelper() {
    private val mapper = ObjectMapper()

    override suspend fun getQuestions(category: String?): List<Question> {
        val apiQuestions =
            RetrofitInstance.theTriviaApi.getQuestions(
                PreferenceHelper.getLimit(),
                category,
                PreferenceHelper.getDifficultyQuery()
            )
        val questions = mutableListOf<Question>()

        apiQuestions.forEach {
            questions += Question(
                question = it.question,
                correctAnswer = it.correctAnswer,
                incorrectAnswers = it.incorrectAnswers
            )
        }
        Log.e("category", apiQuestions.first().category.toString())
        return questions
    }

    override suspend fun getCategories(): List<Category> {
        val categories = RetrofitInstance.theTriviaApi.getCategories()

        runCatching {
            val response = mapper.readTree(
                mapper.writeValueAsString(categories)
            )
            val categoriesList = mutableListOf<Category>()
            response.fields().forEach {
                categoriesList += Category(
                    id = it.value.firstOrNull().toString().replace("\"", ""),
                    name = it.key.toString()
                )
            }
            return categoriesList
        }
        return listOf()
    }

    override suspend fun getStats(): List<String> {
        val metadata = RetrofitInstance.theTriviaApi.getStats()

        val stats = mutableListOf<String>()
        val mapper = ObjectMapper()

        runCatching {
            val stateStats = mapper.readTree(
                mapper.writeValueAsString(metadata.byState)
            )

            stateStats.fields().forEach { field ->
                stats += field.formatStats()
            }

            val categoryStats = mapper.readTree(
                mapper.writeValueAsString(metadata.byCategory)
            )

            categoryStats.fields().forEach {
                stats += it.formatStats()
            }

            val difficultyStats = mapper.readTree(
                mapper.writeValueAsString(metadata.byDifficulty)
            )

            difficultyStats.fields().forEach { field ->
                stats += field.formatStats()
            }
            return stats
        }
        return listOf()
    }
}
