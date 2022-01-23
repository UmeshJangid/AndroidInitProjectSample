package com.app.androidinitialproject.utility

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.androidinitialproject.R


var Activity.showToastShort: String?
    get() = showToastShort
    set(value) {
        if (!TextUtils.isEmpty(value))
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

var Activity.showMessageDialog: String
    get() = showMessageDialog
    set(value) {
        AlertDialog.Builder(this).apply {
            setMessage(value)
            setNeutralButton(
                getString(R.string.ok),
                { dialog, which ->
                    dialog.dismiss()
                })
        }.create().show()
    }

fun Activity.closeKeyboard() {
    this.currentFocus?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(it.windowToken, 0)
    }
}

// Fragment extensions
var Fragment.showToastShort: String?
    get() = showToastShort
    set(value) {
        if (!TextUtils.isEmpty(value))
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }

var Fragment.showMessageDialog: String
    get() = showMessageDialog
    set(value) {
        context?.let {
            AlertDialog.Builder(it).apply {
                setMessage(value)
                setNeutralButton(
                    getString(R.string.ok),
                    { dialog, which ->
                        dialog.dismiss()
                    })
            }.create().show()
        }
    }

fun Fragment.closeKeyboard() {
    context?.let { c ->
        view?.let {
            (c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

// Recycler ViewHolder extensions
// Call inside onBindViewHolder with holder.functionName/holder.varName
// Call inside holder class scope with functionName/varName

var RecyclerView.ViewHolder.showToastShort: String?
    get() = showToastShort
    set(value) {
        if (!TextUtils.isEmpty(value))
            Toast.makeText(itemView.context, value, Toast.LENGTH_SHORT).show()
    }

var RecyclerView.ViewHolder.showMessageDialog: String
    get() = showMessageDialog
    set(value) {
        itemView.context?.let {
            AlertDialog.Builder(it).apply {
                setMessage(value)
                setNeutralButton(
                    it.getString(R.string.ok),
                    { dialog, which ->
                        dialog.dismiss()
                    })
            }.create().show()
        }
    }

fun RecyclerView.ViewHolder.closeKeyboard() {
    itemView?.let {
        itemView.context?.let { c ->
            (c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

// TextView extension
fun TextView.setTextOrEmpty(s: String?) = s?.let { text = s } ?: kotlin.run { text = "" }
fun TextView.setTextOrNA(s: String?) = s?.let { text = s } ?: kotlin.run { text = "NA" }