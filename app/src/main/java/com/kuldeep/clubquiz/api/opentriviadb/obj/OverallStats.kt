package com.kuldeep.clubquiz.api.opentriviadb.obj

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class OverallStats(
    val total_num_of_pending_questions: Int? = null,
    val total_num_of_questions: Int? = null,
    val total_num_of_rejected_questions: Int? = null,
    val total_num_of_verified_questions: Int? = null
)
