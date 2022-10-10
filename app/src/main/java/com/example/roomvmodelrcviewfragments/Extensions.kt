package com.example.roomvmodelrcviewfragments

import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(fragment: Fragment) {
    requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .addToBackStack("")
        .commit()
}