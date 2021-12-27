package com.wangzhumo.distribute

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen {
    HOME,
    SEARCH,
    DETAIL
}

fun Fragment.navigate(to: Screen, from: Screen, bundle: Bundle? = null) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.HOME -> {
            findNavController().navigate(R.id.screen_home,bundle)
        }
        Screen.SEARCH -> {
            findNavController().navigate(R.id.screen_search_page,bundle)
        }
        Screen.DETAIL -> {
            findNavController().navigate(R.id.screen_details_page,bundle)
        }
    }
}
