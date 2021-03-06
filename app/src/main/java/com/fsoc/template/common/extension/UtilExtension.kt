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
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.fsoc.template.R
import com.fsoc.template.presentation.main.customer.add.AddSettingCustomerModel
import com.fsoc.template.presentation.main.menu.MenuMode
import com.fsoc.template.presentation.main.menu.MenuModel
import com.google.gson.Gson
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors


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

fun getMenuList(): List<MenuModel> {
    return arrayListOf(
        MenuModel(
            MenuMode.TRANGCHU,
            "Trang ch???",
            "Imei, h???n s??? d???ng",
            R.drawable.ic_baseline_home_24
        ),
        MenuModel(
            MenuMode.SUATINNHAN,
            "S???a tin nh???n",
            "S???a, t???i l???i tin nh???n",
            R.drawable.ic_edit
        ),
        MenuModel(
            MenuMode.QUANLYTINNHAN,
            "Qu???n l?? tin nh???n",
            "SMS, Zalo, Viber, WhatsApp",
            R.drawable.ic_baseline_email_24
        ),
        MenuModel(
            MenuMode.CHUYENSOGIUSO,
            "Chuy???n s???/Gi??? s???",
            "Chuy???n s??? v?? gi??? s???",
            R.drawable.ic_resource_switch
        ),
        MenuModel(
            MenuMode.BAOCAOTHANGTHUA,
            "B??o c??o th???ng thua",
            "B??o c??o k???t qu??? t???ng kh??ch",
            R.drawable.ic_certificate
        ),
        MenuModel(
            MenuMode.CAN_BANG,
            "C??n b???ng",
            "C??n b???ng tr???c ti???p",
            R.drawable.ic_baseline_home_24
        ),
        MenuModel(
            MenuMode.QUAN_LY_CONG_NO,
            "Qu???n l?? c??ng n???",
            "C??ng n???/Thanh to??n",
            R.drawable.ic_analysis
        ),
        MenuModel(
            MenuMode.DANH_SACH_KHACH_HANG,
            "Danh s??ch kh??ch h??ng",
            "Th??ng tin kh??ch h??ng",
            R.drawable.ic_baseline_supervised_user_circle_24
        ),
        MenuModel(
            MenuMode.CAI_DAT,
            "C??i ?????t",
            "C??i ?????t cho ???ng d???ng",
            R.drawable.ic_baseline_settings_24
        ),
        MenuModel(
            MenuMode.CAC_TIN_NHAN_MAU,
            "C??c tin nh???n m???u",
            "C??c c?? ph??p chu???n",
            R.drawable.ic_contract
        ),
        MenuModel(
            MenuMode.CO_SO_DU_DU_LIEU,
            "C?? s??? d??? li???u",
            "C???p nh???t KQ/T??nh ti???n",
            R.drawable.ic_database_configuration
        )
    )
}



fun getSettingCustomerList(): List<AddSettingCustomerModel> {
    return listOf(
        AddSettingCustomerModel(
            "Gi?? ?????", null, null
        ),
        AddSettingCustomerModel(
            "?????u ??B (dea): "
        ),
        AddSettingCustomerModel(
            "????t ??B (deb): "
        ),
        AddSettingCustomerModel(
            "?????u nh???t (dec): "
        ),
        AddSettingCustomerModel(
            "????t nh???t (ded): ", 0.76F
        ),
        AddSettingCustomerModel(
            "?????u ??n 80 (det): ", 0.82F, 80F
        ),
        AddSettingCustomerModel("Gi?? l??", null, null),
        AddSettingCustomerModel(
            "L?? ", 21.8F, 80f
        ),
        AddSettingCustomerModel("Gi?? xi??n"),
        AddSettingCustomerModel("Xi??n 2:", 0.7f, 10f),
        AddSettingCustomerModel("Xi??n 3:", 0.7f, 40f),
        AddSettingCustomerModel("Xi??n 4:", 0.7f, 100f),
        AddSettingCustomerModel("Xi??n nh??y:", 1.0f, 10f),
        AddSettingCustomerModel("Gi?? 3 c??ng"),
        AddSettingCustomerModel("Ba c??ng", 0.9f, 400f),
    )
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

@SuppressLint("HardwareIds")
fun Fragment.getIMEI(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
    } else {
        val mTelephony =
            requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.str_ok)) { dialog, which ->
        callback()
        dialog.dismiss()
    }
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.str_cancel)) { dialog, which ->
        dialog.dismiss()
    }
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun Fragment.showConfirmDialog(message: String, callback: () -> Unit) {
    val alertDialog = AlertDialog.Builder(requireContext()).create()
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.str_ok)) { dialog, which ->
        callback()
        dialog.dismiss()
    }
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.str_cancel)) { dialog, which ->
        dialog.dismiss()
    }
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun getCurrentDate():String{
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(Date())
}

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}