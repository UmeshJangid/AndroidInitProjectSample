package com.app.androidinitialproject.domain.dialogs

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.app.androidinitialproject.R
import com.app.androidinitialproject.domain.annotations.DataRequestType

/**
 * The type Cancelable retry dialog.
 */
class CancelableRetryDialog {
    private var alertDialog: AlertDialog? = null

    private constructor() {}
    /**
     * Instantiates a new Cancelable retry dialog.
     *
     * @param context        the context
     * @param requesterType  the requester type
     * @param message        the message
     * @param dialogListener the dialog listener
     */
    constructor(
        context: Context?,
        @DataRequestType requesterType: Int,
        message: String?,
        dialogListener: CancelableRetryDialogListener?
    ) {
        val builder =
            AlertDialog.Builder(context!!)
        if (TextUtils.isEmpty(message)) builder.setMessage(R.string.err_something_wrong) else builder.setMessage(
            message
        )
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.retry) { dialog, which ->
            dialog.dismiss()
            dialogListener?.onRetry(dialog, requesterType)
        }
        builder.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
            dialogListener?.onCancel(dialog, requesterType)
        }
        alertDialog = builder.create()
    }

    /**
     * Show.
     */
    fun show() {
        alertDialog!!.show()
    }

    /**
     * The interface Cancelable retry dialog listener.
     */
    interface CancelableRetryDialogListener {
        /**
         * On retry.
         *
         * @param dialog        the dialog
         * @param requesterType the requester type
         */
        fun onRetry(dialog: DialogInterface?, @DataRequestType requesterType: Int)

        /**
         * On cancel.
         *
         * @param dialog        the dialog
         * @param requesterType the requester type
         */
        fun onCancel(dialog: DialogInterface?, @DataRequestType requesterType: Int)
    }
}