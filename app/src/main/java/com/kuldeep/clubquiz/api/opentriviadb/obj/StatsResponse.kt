package com.kuldeep.clubquiz.api.opentriviadb.obj

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class StatsResponse(
    val categories: Any? = null,
    val overall: OverallStats? = null
)
