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
            "Trang chủ",
            "Imei, hạn sử dụng",
            R.drawable.ic_baseline_home_24
        ),
        MenuModel(
            MenuMode.SUATINNHAN,
            "Sửa tin nhắn",
            "Sửa, tải lại tin nhắn",
            R.drawable.ic_edit
        ),
        MenuModel(
            MenuMode.QUANLYTINNHAN,
            "Quản lý tin nhắn",
            "SMS, Zalo, Viber, WhatsApp",
            R.drawable.ic_baseline_email_24
        ),
        MenuModel(
            MenuMode.CHUYENSOGIUSO,
            "Chuyển số/Giữ số",
            "Chuyển số và giữ số",
            R.drawable.ic_resource_switch
        ),
        MenuModel(
            MenuMode.BAOCAOTHANGTHUA,
            "Báo cáo thắng thua",
            "Báo cáo kết quả từng khách",
            R.drawable.ic_certificate
        ),
        MenuModel(
            MenuMode.CAN_BANG,
            "Cân bảng",
            "Cân bảng trực tiếp",
            R.drawable.ic_baseline_home_24
        ),
        MenuModel(
            MenuMode.QUAN_LY_CONG_NO,
            "Quản lý công nợ",
            "Công nợ/Thanh toán",
            R.drawable.ic_analysis
        ),
        MenuModel(
            MenuMode.DANH_SACH_KHACH_HANG,
            "Danh sách khách hàng",
            "Thông tin khách hàng",
            R.drawable.ic_baseline_supervised_user_circle_24
        ),
        MenuModel(
            MenuMode.CAI_DAT,
            "Cài đặt",
            "Cài đặt cho ứng dụng",
            R.drawable.ic_baseline_settings_24
        ),
        MenuModel(
            MenuMode.CAC_TIN_NHAN_MAU,
            "Các tin nhắn mẫu",
            "Các cú pháp chuẩn",
            R.drawable.ic_contract
        ),
        MenuModel(
            MenuMode.CO_SO_DU_DU_LIEU,
            "Cơ sở dữ liệu",
            "Cập nhật KQ/Tính tiền",
            R.drawable.ic_database_configuration
        )
    )
}



fun getSettingCustomerList(): List<AddSettingCustomerModel> {
    return listOf(
        AddSettingCustomerModel(
            "Giá đề", null, null
        ),
        AddSettingCustomerModel(
            "Đầu ĐB (dea): "
        ),
        AddSettingCustomerModel(
            "Đít ĐB (deb): "
        ),
        AddSettingCustomerModel(
            "Đầu nhất (dec): "
        ),
        AddSettingCustomerModel(
            "Đít nhất (ded): ", 0.76F
        ),
        AddSettingCustomerModel(
            "Đầu ăn 80 (det): ", 0.82F, 80F
        ),
        AddSettingCustomerModel("Giá lô", null, null),
        AddSettingCustomerModel(
            "Lô ", 21.8F, 80f
        ),
        AddSettingCustomerModel("Giá xiên"),
        AddSettingCustomerModel("Xiên 2:", 0.7f, 10f),
        AddSettingCustomerModel("Xiên 3:", 0.7f, 40f),
        AddSettingCustomerModel("Xiên 4:", 0.7f, 100f),
        AddSettingCustomerModel("Xiên nháy:", 1.0f, 10f),
        AddSettingCustomerModel("Giá 3 càng"),
        AddSettingCustomerModel("Ba càng", 0.9f, 400f),
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