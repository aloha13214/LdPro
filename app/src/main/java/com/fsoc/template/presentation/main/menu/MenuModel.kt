package com.fsoc.template.presentation.main.menu

data class MenuModel(val key: MenuMode, val label: String, val description: String, val icon: Int)
enum class MenuMode {
    TRANGCHU,
    SUATINNHAN,
    QUANLYTINNHAN,
    CHUYENSOGIUSO,
    BAOCAOTHANGTHUA,
    CHAY_TRANG,
    CAN_BANG,
    XO_SO_TRUC_TIEP,
    QUAN_LY_CONG_NO,
    DANH_SACH_KHACH_HANG,
    CAI_DAT,
    CAC_TIN_NHAN_MAU,
    CO_SO_DU_DU_LIEU
}