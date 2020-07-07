package com.example.mvvm_kotlin_ut.userlist

import com.example.mvvm_kotlin_ut.userlist.model.Data

/**
 * @author Ravi
 */
interface ItemClickListener {
    fun setClickedInfo(data: Data)
}