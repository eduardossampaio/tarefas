package com.apps.esampaio.new_version.view.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class DisableViewTextWatcher(private val targetView:View, private val disableOnInit: Boolean = false) : TextWatcher {
    init{
        targetView.isEnabled = !disableOnInit;
    }
    override fun afterTextChanged(text: Editable?) {

    }

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
        targetView.isEnabled = after != 0
    }
}