package com.app.androidinitialproject.domain.dialogs

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.app.androidinitialproject.R
import com.app.androidinitialproject.domain.annotations.DataRequestType

/**
 * The type Force retry dialog.
 */
class ForceRetryDialog {
    private var alertDialog: AlertDialog? = null

    private constructor() {}
    /**
     * Instantiates a new Force retry dialog.
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
        dialogListener: ForceRetryDialogListener?
    ) {
        val builder =
            AlertDialog.Builder(context!!)
        if (TextUtils.isEmpty(message)) builder.setMessage(R.string.err_something_wrong) else builder.setMessage(
            message
        )
        builder.setCancelable(false)
        builder.setNeutralButton("Retry") { dialog, which ->
            dialog.dismiss()
            dialogListener?.onRetry(dialog, requesterType)
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
     * The interface Force retry dialog listener.
     */
    interface ForceRetryDialogListener {
        /**
         * On retry.
         *
         * @param dialog        the dialog
         * @param requesterType the requester type
         */
        fun onRetry(dialog: DialogInterface?, @DataRequestType requesterType: Int)
    }
}