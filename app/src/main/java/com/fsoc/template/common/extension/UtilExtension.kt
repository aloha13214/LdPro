package com.fsoc.template.common.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.fsoc.template.R
import com.fsoc.template.presentation.main.menu.MenuMode
import com.fsoc.template.presentation.main.menu.MenuModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

val regexPhone = "(84|0[3|5|7|8|9])+([0-9]{8})\\b".toRegex()
fun checkRegex(regex: Regex, inputString: String) = regex.containsMatchIn(inputString)

fun Context.hideKeyboardFrom(view: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    this.hideKeyboardFrom(view)
}

fun Fragment.hideKeyboard() {
    context?.hideKeyboardFrom(requireView())
}

/**
 * Hide keyboard when User touch out site input is EditText  or search view
 */
fun Fragment.hideKeyBoardWhenTouchOutside() {
    view?.hideKeyBoardWhenTouchOutside()
}

fun View.hideKeyBoardWhenTouchOutside(viewFocus: View? = null) {
    if (this !is EditText) {
        this.setOnTouchListener { _, _ ->
            hideKeyBoard()
            viewFocus?.requestFocus()
            false
        }
    }
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.getString(stringId: Int): String {
    return this.resources.getString(stringId)
}

fun EditText.addSimpleTextWatcher(onTextChanged: (CharSequence?) -> Unit) {
    addTextChangedListener(createSimpleTextWatcher(onTextChanged))
}

private fun createSimpleTextWatcher(onTextChanged: (CharSequence?) -> Unit) =
    object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            /* NOP */
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            /* NOP */
            onTextChanged(s)
        }
    }

fun AppCompatSpinner.addSimpleOnItemSelectedListener(onItemSelected: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}

const val INTERVAL_SEEKBAR = 5
fun AppCompatSeekBar.addSimpleSeekbarListener(onProgress: (String) -> Unit, onStop: (Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val realResult = progress * INTERVAL_SEEKBAR
            onProgress("$realResult%")
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.let {
                val realResult = seekBar.progress * INTERVAL_SEEKBAR
                onStop(realResult)
            } ?: run { onStop(0) }
        }
    })
}

@SuppressLint("HardwareIds")
fun Fragment.getIMEI(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
    } else {
        val mTelephony =
            requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return ""
            }
        }
        if (mTelephony.deviceId != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTelephony.imei
            } else {
                mTelephony.deviceId
            }
        } else {
            Settings.Secure.getString(
                requireContext().contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }
}

fun Activity.showConfirmDialog(message: String, callback: () -> Unit) {
    val alertDialog = AlertDialog.Builder(this).create()
    alertDialog.setMessage(message)
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE,
        resources.getString(R.string.str_ok)
    ) { dialog, which ->
        callback()
        dialog.dismiss()
    }
    alertDialog.setButton(
        AlertDialog.BUTTON_NEGATIVE,
        resources.getString(R.string.str_cancel)
    ) { dialog, which ->
        dialog.dismiss()
    }
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun Fragment.showConfirmDialog(message: String, callback: () -> Unit) {
    val alertDialog = AlertDialog.Builder(requireContext()).create()
    alertDialog.setMessage(message)
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE,
        resources.getString(R.string.str_ok)
    ) { dialog, which ->
        callback()
        dialog.dismiss()
    }
    alertDialog.setButton(
        AlertDialog.BUTTON_NEGATIVE,
        resources.getString(R.string.str_cancel)
    ) { dialog, which ->
        dialog.dismiss()
    }
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(Date())
}

fun Long.convertTimeMessage(): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm:ss")
    return simpleDateFormat.format(this)
}

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}