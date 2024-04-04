package com.kuldeep.clubquiz.util

import com.kuldeep.clubquiz.api.opentriviadb.OpenTriviaDBHelper
import com.kuldeep.clubquiz.api.thetriviaapi.TheTriviaApiHelper
import com.kuldeep.clubquiz.obj.ApiType

object ApiInstance {
    var apiHelper = when (
        PreferenceHelper.getApi()
    ) {
        ApiType.theTriviaApi -> TheTriviaApiHelper()
        ApiType.openTriviaApi -> OpenTriviaDBHelper()
        else -> TheTriviaApiHelper()
    }

    fun updateApiHelper(apiPref: Int) {
        apiHelper = when (apiPref) {
            ApiType.theTriviaApi -> TheTriviaApiHelper()
            ApiType.openTriviaApi -> OpenTriviaDBHelper()
            else -> TheTriviaApiHelper()
        }
    }
}
