package com.wangzhumo.distribute

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen {
    HOME,
    SEARCH
}

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.HOME -> {
            findNavController().navigate(R.id.screen_home)
        }
        Screen.SEARCH -> {
            findNavController().navigate(R.id.screen_search_page)
        }
    }
}
