package org.wandukong.app.util

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import org.wandukong.app.R

class EditTextEventListener{

    fun editTextIsFocus(view : EditText){
        view.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                v.setBackgroundResource(R.drawable.edit_text)
            }else{
                view.setBackgroundColor(Color.parseColor("#f3f3f3"))
            }
        }
    }

    fun editTextIsChanged(view : EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()){
                    view.setBackgroundColor(Color.parseColor("#f3f3f3"))
                }else{
                    view.setBackgroundResource(R.drawable.edit_text)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}