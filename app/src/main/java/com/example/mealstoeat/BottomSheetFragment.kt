package com.example.mealstoeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(private val nameValid: Boolean,
                          private val ratingValid: Boolean,
                          private val imgValid: Boolean,
                          private val valid: Boolean) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Validation messages
        super.onViewCreated(view, savedInstanceState)
        if (!nameValid){
            view.findViewById<TextView>(R.id.nameWarning).text = getText(R.string.nameWarning)
            view.findViewById<TextView>(R.id.nameWarning).updateLayoutParams { height = WRAP_CONTENT }
        }

        if(!ratingValid) {
            view.findViewById<TextView>(R.id.ratingWarning).text = getText(R.string.ratingWarning)
            view.findViewById<TextView>(R.id.ratingWarning).updateLayoutParams { height = WRAP_CONTENT }
        }

        if(!imgValid) {
            view.findViewById<TextView>(R.id.imgWarning).text = getText(R.string.imgWarning)
            view.findViewById<TextView>(R.id.imgWarning).updateLayoutParams { height = WRAP_CONTENT }
        }

        if (valid) {
            view.findViewById<TextView>(R.id.success).text = getText(R.string.success)
        } else {
            view.findViewById<TextView>(R.id.success).text = getText(R.string.nosuccess)
        }
    }
}