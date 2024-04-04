package com.kuldeep.clubquiz.obj

data class Question(
    val question: String? = null,
    val correctAnswer: String? = null,
    val incorrectAnswers: List<String>? = null
)
