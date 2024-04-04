package com.kuldeep.clubquiz.extensions

fun String?.wordCount(): Int {
    return this!!
        .trim()
        .split(
            "\\s+"
                .toRegex()
        ).size
}
