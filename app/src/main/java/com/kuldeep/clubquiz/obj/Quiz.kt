package com.kuldeep.clubquiz.obj

data class Quiz(
    var name: String? = null,
    var creator: Boolean? = null,
    var questions: List<Question>? = null,
    var position: Int = 0
)
