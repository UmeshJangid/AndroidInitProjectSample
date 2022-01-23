package com.app.androidinitialproject.domain.dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 * The type App date picker dialog.
 */
class AppDatePickerDialog : DialogFragment(),
    OnDateSetListener {
    private var dialogListener: AppDatePickerDialogListener? = null
    private var shouldDisablePastDates = false
    private var shouldDisableUpcomingDates = false
    private var startDate: Date? = null
    private var endDate: Date? = null
    private var currentDate: Date? = null
    /**
     * Sets dialog listener.
     *
     * @param dialogListener the dialog listener
     */
    fun setDialogListener(dialogListener: AppDatePickerDialogListener?) {
        this.dialogListener = dialogListener
    }

    /**
     * Sets should disable past dates.
     *
     * @param shouldDisablePastDates the should disable past dates
     */
    fun setShouldDisablePastDates(shouldDisablePastDates: Boolean) {
        this.shouldDisablePastDates = shouldDisablePastDates
    }

    fun setShouldDisableUpcomingDates(shouldDisableUpcomingDates: Boolean) {
        this.shouldDisableUpcomingDates = shouldDisableUpcomingDates
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    fun setStartDate(startDate: Date?) {
        this.startDate = startDate
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    fun setEndDate(endDate: Date?) {
        this.endDate = endDate
    }

    fun setCurrentDate(currentDate: Date?) {
        this.currentDate = currentDate
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(activity!!, this, year, month, day)
        if (shouldDisablePastDates) dialog.datePicker.minDate =
            Calendar.getInstance().timeInMillis - 1000
        if (shouldDisableUpcomingDates) dialog.datePicker.maxDate =
            Calendar.getInstance().timeInMillis - 1000
        if (startDate != null) dialog.datePicker.minDate = startDate!!.time
        if (endDate != null) dialog.datePicker.maxDate = endDate!!.time
        if (currentDate != null) {
            val c1 = Calendar.getInstance()
            c1.time = currentDate
            val year1 = c1[Calendar.YEAR]
            val month1 = c1[Calendar.MONTH]
            val day1 = c1[Calendar.DAY_OF_MONTH]
            dialog.datePicker.updateDate(year1, month1, day1)
        }
        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if (dialogListener != null) dialogListener!!.onDateSet(view, year, month, day)
    }

    /**
     * The interface App date picker dialog listener.
     */
    interface AppDatePickerDialogListener {
        /**
         * On date set.
         *
         * @param view  the view
         * @param year  the year
         * @param month the month
         * @param day   the day
         */
        fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int)
    }

    companion object {
        /**
         * Create and show dialog.
         *
         * @param pickerId               the picker id
         * @param shouldDisablePastDates the should disable past dates
         * @param fragmentManager        the fragment manager
         * @param dialogListener         the dialog listener
         */
        fun createAndShowDialog(
            pickerId: Int,
            shouldDisablePastDates: Boolean,
            fragmentManager: FragmentManager?,
            dialogListener: AppDatePickerDialogListener
        ) {
            val datePickerDialog = AppDatePickerDialog()
            datePickerDialog.setDialogListener(dialogListener)
            datePickerDialog.setShouldDisablePastDates(shouldDisablePastDates)
            datePickerDialog.show(fragmentManager!!, pickerId.toString())
        }

        fun createAndShowDialog(
            pickerId: Int,
            shouldDisablePastDates: Boolean,
            currentDate: Date?,
            fragmentManager: FragmentManager?,
            dialogListener: AppDatePickerDialogListener
        ) {
            val datePickerDialog = AppDatePickerDialog()
            datePickerDialog.setDialogListener(dialogListener)
            if (currentDate != null) datePickerDialog.setCurrentDate(currentDate)
            datePickerDialog.setShouldDisablePastDates(shouldDisablePastDates)
            datePickerDialog.show(fragmentManager!!, pickerId.toString())
        }
        //    public static void createAndShowDialogDob(int pickerId, boolean shouldDisableUpcomingDates, Date currentDate, FragmentManager fragmentManager,
//                                           @NonNull AppDatePickerDialogListener dialogListener) {
//        AppDatePickerDialog datePickerDialog = new AppDatePickerDialog();
//        datePickerDialog.setDialogListener(dialogListener);
//        if (currentDate != null) datePickerDialog.setCurrentDate(currentDate);
//        datePickerDialog.setShouldDisableUpcomingDates(shouldDisableUpcomingDates);
//        datePickerDialog.show(fragmentManager, String.valueOf(pickerId));
//    }
        /**
         * Create and show dialog.
         *
         * @param pickerId        the picker id
         * @param startDate       the start date
         * @param endDate         the end date
         * @param fragmentManager the fragment manager
         * @param dialogListener  the dialog listener
         */
        fun createAndShowDialog(
            pickerId: Int,
            startDate: Date?,
            endDate: Date?,
            fragmentManager: FragmentManager?,
            dialogListener: AppDatePickerDialogListener
        ) {
            val datePickerDialog = AppDatePickerDialog()
            datePickerDialog.setDialogListener(dialogListener)
            if (startDate != null) datePickerDialog.setStartDate(startDate)
            if (endDate != null) datePickerDialog.setEndDate(endDate)
            datePickerDialog.show(fragmentManager!!, pickerId.toString())
        }

        fun createAndShowDialog(
            pickerId: Int,
            startDate: Date?,
            endDate: Date?,
            currentDate: Date?,
            fragmentManager: FragmentManager?,
            dialogListener: AppDatePickerDialogListener
        ) {
            val datePickerDialog = AppDatePickerDialog()
            datePickerDialog.setDialogListener(dialogListener)
            if (startDate != null) datePickerDialog.setStartDate(startDate)
            if (endDate != null) datePickerDialog.setEndDate(endDate)
            if (currentDate != null) datePickerDialog.setCurrentDate(currentDate)
            datePickerDialog.show(fragmentManager!!, pickerId.toString())
        }
    }
}