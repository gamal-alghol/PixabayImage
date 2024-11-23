package com.gamal.Pixabay.utils

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.gamal.Pixabay.R

fun isEmailValid(email: String): Boolean {
    return if (email.contains('@') && email.isNotBlank()) {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } else {
        false
    }
}
fun isPasswordValid(editTexts: List<EditText>): Boolean {
    for (editText in editTexts) {
        val length = editText.text.toString().length
        if (length !in 6..12) {
            return false
        }
    }
    return true
}
fun isAgeValid(editTexts: List<EditText>): Boolean {
    for (editText in editTexts) {
        val age = editText.text.toString().toInt()
        if (age !in 18..99) {
            return false
        }
    }
    return true
}

fun areAllFieldsFilled(editTexts: List<EditText>,context: Context): Boolean {
    for (editText in editTexts) {
        if (editText.text.toString().trim().isEmpty()) {

            // Optionally, set an error message on the empty EditText
            editText.error = ContextCompat.getString(context,R.string.field_required)
            return false
        }
    }
    return true
}