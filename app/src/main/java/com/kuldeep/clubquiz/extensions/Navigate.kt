package com.kuldeep.clubquiz.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kuldeep.clubquiz.R

fun FragmentManager?.navigate(
    fragment: Fragment,
    addToBackStack: Boolean = true
) {
    val ft = this!!.beginTransaction()
        .replace(R.id.fragment, fragment)

    if (addToBackStack) ft.addToBackStack(null)
    ft.commit()
}
