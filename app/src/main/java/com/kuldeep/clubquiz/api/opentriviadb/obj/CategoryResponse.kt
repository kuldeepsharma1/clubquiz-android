package com.kuldeep.clubquiz.api.opentriviadb.obj

import com.kuldeep.clubquiz.obj.Category
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoryResponse(
    val trivia_categories: List<Category>? = null
)
