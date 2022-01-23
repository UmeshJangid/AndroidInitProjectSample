package com.app.androidinitialproject.domain.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 * The type App time picker dialog.
 */
class AppTimePickerDialog : DialogFragment(),
    OnTimeSetListener {
    private var dialogListener: AppTimePickerDialogListener? = null
    /**
     * Sets dialog listener.
     *
     * @param dialogListener the dialog listener
     */
    fun setDialogListener(dialogListener: AppTimePickerDialogListener?) {
        this.dialogListener = dialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        return TimePickerDialog(
            activity,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if (dialogListener != null) dialogListener!!.onTimeSet(view, hourOfDay, minute)
    }

    /**
     * The interface App time picker dialog listener.
     */
    interface AppTimePickerDialogListener {
        /**
         * On time set.
         *
         * @param view      the view
         * @param hourOfDay the hour of day
         * @param minute    the minute
         */
        fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int)
    }

    companion object {
        /**
         * Create and show dialog.
         *
         * @param pickerId        the picker id
         * @param fragmentManager the fragment manager
         * @param dialogListener  the dialog listener
         */
        fun createAndShowDialog(
            pickerId: Int, fragmentManager: FragmentManager?,
            dialogListener: AppTimePickerDialogListener
        ) {
            val datePickerDialog = AppTimePickerDialog()
            datePickerDialog.setDialogListener(dialogListener)
            datePickerDialog.show(fragmentManager!!, pickerId.toString())
        }
    }
}