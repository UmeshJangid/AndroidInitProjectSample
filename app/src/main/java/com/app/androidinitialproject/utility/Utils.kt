package com.app.androidinitialproject.utility

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.app.androidinitialproject.R
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by : Umesh Jangid
 * Date : 31 March 2020
 *
 */
object Utils {

    fun <T> nullCheck(str: T?): T {
        return str ?: "" as T
    }

    private const val EMAIL_PATTERN =
        "^[\\p{L}\\p{N}\\._%+-]+@[\\p{L}\\p{N}\\.\\-]+\\.[\\p{L}]{2,}$"
    private const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@_*#\$%^&+=])(?=\\S+\$).{6,}\$"
    private var toast: Toast? = null
    private val progressDialog: ProgressDialog? = null
    private var snackbar: Snackbar? = null
    val OOPS_STRING: String = "Oops! Something went wrong, Try Again!"
    var dialog: Dialog? = null


    fun hideDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    /**
     * This method return date in this format :  2015/01/02 23:14:05
     * */
    fun getCurrentTimeInFormat(): String {
        val c = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val strDate = dateFormat.format(c.time)
        return strDate
    }

    fun isValidPassword(target: String): Boolean {
        return (target.matches(Regex(PASSWORD_REGEX)))
    }

    /* Checks if external storage is available for read and write */
    val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    /* Checks if external storage is available to at least read */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    fun showDialog(context: Context) {
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss()
            dialog = null
        }
        dialog = Dialog(context, R.style.styleDialogTransparent)
        dialog!!.setContentView(R.layout.progressview_dialog)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    @JvmStatic
    fun showToast(context: Context, message: String) {
        showToast(context, message, false)// false mean DURATION_SHORT
    }

    fun showToast(context: Context, message: String, durationShort: Boolean) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(
            context,
            message,
            if (durationShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        )
        toast!!.show()
    }

    fun showSnackBar(
        view: View,
        message: String,
        actionName1: String?,
        listener1: View.OnClickListener?,
        duration: Int
    ) {
        showSnackBar(view, message, actionName1, listener1, null, null, duration)
    }

    fun showSnackBar(
        view: View,
        message: String,
        actionName1: String?,
        listener1: View.OnClickListener?,
        actionName2: String?,
        listener2: View.OnClickListener?,
        duration: Int
    ) {
        if (snackbar != null) {
            snackbar!!.dismiss()
        }
        snackbar = Snackbar.make(view, message, duration)
        val snackbarView = snackbar!!.getView()
        snackbarView.setBackgroundColor(Color.parseColor("#ec3338"))
        //  snackbar.(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
        if (actionName1 != null && listener1 != null) {
            snackbar!!.setAction(actionName1, listener1)
        }
        if (actionName2 != null && listener2 != null) {
            snackbar!!.setAction(actionName2, listener2)
        }
        snackbar!!.show()
    }

    fun dismissSnackbar() {
        if (snackbar != null) {
            snackbar!!.dismiss()
        }
    }


    fun getImageUri(inImage: Bitmap, context: Context): Uri {
        // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    // Returns true if external storage for photos is available
    private fun isExternalStorageAvailable(): Boolean {
        val state = Environment.getExternalStorageState()
        return state == Environment.MEDIA_MOUNTED
    }



    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }


    fun getFilename(context: Context): String {
        val mediaStorageDir =
            File(Environment.getExternalStorageDirectory().toString() + "/ProjectName")
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        val mImageName = "IMG_" + System.currentTimeMillis().toString() + ".jpg"
        return mediaStorageDir.absolutePath + "/" + mImageName
    }


    fun commaSeparatedStringToArrayList(commaSeparated: String?): ArrayList<String>? {
        var list: ArrayList<String>? = null
        if (commaSeparated != null && commaSeparated.isNotEmpty()) {
            list =
                ArrayList(Arrays.asList(*commaSeparated.split(",".toRegex()).dropLastWhile { it.isNotEmpty() }.toTypedArray()))
        }
        return list
    }

    fun arrayListToCommaSeparatedString(list: ArrayList<String>): String? {
        var s: String? = null
        if (list.size > 0) {
            s = TextUtils.join(",", list.toTypedArray())
        }
        return s
    }

    @JvmStatic
    fun shakeView(view: View, context: Context) {
        val animShake = AnimationUtils.loadAnimation(context, R.anim.animation)
        view.startAnimation(animShake)
    }


    @JvmStatic
    fun shakeItemView(view: View, context: Context) {
        val animShake = AnimationUtils.loadAnimation(context, R.anim.shake)
        val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, 10))
        } else {
            vibrator.vibrate(100)
        }

        view.startAnimation(animShake)
    }

    /*Validate Password*/

    public fun callAlertDialog(context: Context, title: String, message: String, positiveButtonText: String?, negativeButtonText: String?, showOnlyOneButton: Boolean, callback: CustomizedAlertDialogCallback<String>) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        alert.setIcon(R.mipmap.ic_launcher)
        alert.setMessage(message)
        alert.setPositiveButton(
            positiveButtonText ?: "Ok"
        ) { dialog, whichButton -> callback.alertDialogCallback("1") }
        if (!showOnlyOneButton) {
            alert.setNegativeButton(
                negativeButtonText ?: "Cancel"
            ) { dialog, whichButton ->
                // Canceled.
                callback.alertDialogCallback("0")
            }
        }
        alert.setCancelable(false)
        alert.show()
    }


    /* Go to App Settings */

    fun goToAppSetting(context: Context) {
        val builder = AlertDialog.Builder(context)
            .setMessage(
                "For proper functioning of app you need to provide all permisson to the app." +
                        "this won't harm your phone or your data." +
                        "For that go to Enable Now -> Permissions")
            .setPositiveButton("Enable Now") { dialog, which ->
                dialog.dismiss()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        builder.create().show()
    }


    /**
     * @param currentdateFormat = "What your date format is pass in this variable"
     * @param requireFormat ="Format you require"
     * @param dateStr = "Your date in string format"
    * */
   /* fun changeDateToFormat(currentdateFormat: String, requireFormat: String, dateStr: String): String {
        var result: String? = null
        if (dateStr.isNullOrEmpty()) {
            return result!!
        }
        val formatterOld = SimpleDateFormat(currentdateFormat, Locale.getDefault())
        val formatterNew = SimpleDateFormat(requireFormat, Locale.getDefault())
        var date: Date? = null
        try {
            date = formatterOld.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            result = getFormattedDate(date)
        }
        return result!!

    }*/


    /**
     * Is network connected available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    fun isNetworkConnectedAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }


    /**
     * Is valid email boolean.
     * @param email the email
     * @return the boolean
     */
    fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) false else Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Is valid phone number boolean.
     *
     * @param number the number
     * @return the boolean
     */
    fun isValidPhoneNumber(number: String): Boolean {
        return if (TextUtils.isEmpty(number)) false else Patterns.PHONE.matcher(number).matches()
//        return ((number.length() == 10 && !number.startsWith("0", 0)) || number.length() > 10)
        // && Patterns.PHONE.matcher(number.trim()).matches();
    }

    /**
     * Close key board.
     *
     * @param context the context
     */
    fun closeKeyBoard(context: Context) {
        if (context is Activity) {
            val view = context.currentFocus
            if (view != null) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    /**
     * Close key board.
     *
     * @param context the context
     * @param view    the view
     */
    fun closeKeyBoard(context: Context, view: View?) {
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Gets rounded corner bitmap.
     *
     * @param bitmap the bitmap
     * @param pixels the pixels
     * @return the rounded corner bitmap
     */
    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    /**
     * Gets bitmap from drawable.
     *
     * @param drawable the drawable
     * @return the bitmap from drawable
     */
    fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            val bitmap: Bitmap
            val COLORDRAWABLE_DIMENSION = 2
            val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(
                    COLORDRAWABLE_DIMENSION,
                    COLORDRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * Print key hash.
     *
     * @param context the context
     */
    fun printKeyHash(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Logger.print("Hash Key : $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: Exception) {
        }

    }

    fun convertSpToPx(context: Context, sp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun convertDpToPx(context: Context, dps: Int): Int {
        val metrics = DisplayMetrics()
        if (context is Activity) {
            context.windowManager.defaultDisplay.getMetrics(metrics)
            return (metrics.density * dps).toInt()
        } else
            return 0
    }
}
