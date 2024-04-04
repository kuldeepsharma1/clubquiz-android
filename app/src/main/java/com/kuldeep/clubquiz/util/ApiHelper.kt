package com.kuldeep.clubquiz.util

import com.kuldeep.clubquiz.obj.Category
import com.kuldeep.clubquiz.obj.Question

/**
 * API Scrapper
 */
abstract class ApiHelper {
    abstract suspend fun getQuestions(category: String?): List<Question>

    abstract suspend fun getCategories(): List<Category>

    abstract suspend fun getStats(): List<String>
}
