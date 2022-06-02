package com.example.beerstest.core.extensions

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

val Fragment.supportActionBar: ActionBar?
    get() = (requireActivity() as? AppCompatActivity)?.supportActionBar

fun Fragment.setSupportActionBar(toolbar: Toolbar?) {
    (requireActivity() as? AppCompatActivity)?.setSupportActionBar(toolbar)
}
