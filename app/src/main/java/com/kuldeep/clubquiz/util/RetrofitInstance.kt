package com.kuldeep.clubquiz.util

import com.kuldeep.clubquiz.OPEN_TRIVIA_URL
import com.kuldeep.clubquiz.TRIVIA_API_URL
import com.kuldeep.clubquiz.api.opentriviadb.OpenTriviaDB
import com.kuldeep.clubquiz.api.thetriviaapi.TheTriviaApi
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInstance {

    val theTriviaApi: TheTriviaApi = Retrofit.Builder()
        .baseUrl(TRIVIA_API_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(TheTriviaApi::class.java)

    val openTriviaApi: OpenTriviaDB = Retrofit.Builder()
        .baseUrl(OPEN_TRIVIA_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(OpenTriviaDB::class.java)
}
