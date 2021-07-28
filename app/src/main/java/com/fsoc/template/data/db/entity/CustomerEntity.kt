package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["phone_number"], unique = true)])
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "customer_name") var customerName: String = "",
    @ColumnInfo(name = "phone_number") var phoneNumber: String = "",
    @ColumnInfo(name = "type_customer") var typeCustomer: Int = 0,
    @ColumnInfo(name = "caidat_gia") var settingPrice: String = "",
    @ColumnInfo(name = "caidat_thoigian") var settingTime: String = ""
) {
    constructor(
        customerName: String,
        phoneNumber: String,
        typeCustomer: Int,
        settingPrice: String,
        settingTime: String
    ) : this(0, customerName, phoneNumber, typeCustomer, settingPrice, settingTime)
}

data class SettingPrice(
    val dea: String = "0.72",
    val an_dea: String = "70",
    val deb: String = "0.72",
    val an_deb: String = "70",
    val dec: String = "0.72",
    val an_dec: String = "70",
    val ded: String = "0.72",
    val an_ded: String = "70",
    val det: String = "0.82",
    val an_det: String = "80",
    val lo: String = "21.8",
    val an_lo: String = "80",
    val gia_x2: String = "0.7",
    val an_x2: String = "10",
    val gia_x3: String = "0.7",
    val an_x3: String = "40",
    val gia_x4: String = "0.7",
    val an_x4: String = "100",
    val gia_xn: String = "1.0",
    val an_xn: String = "10",
    val gia_bc: String = "0.7",
    val an_bc: String = "400"
)


data class SettingTime(
    var dlgiu_de: Int = 0,
    var dlgiu_lo: Int = 0,
    var dlgiu_xi: Int = 0,
    var dlgiu_xn: Int = 0,
    var dlgiu_bc: Int = 0,
    var khgiu_de: Int = 0,
    var khgiu_lo: Int = 0,
    var khgiu_xi: Int = 0,
    var khgiu_xn: Int = 0,
    var khgiu_bc: Int = 0,
    var ok_tin: Int = 0,
    var xien_nhan: Int = 0,
    var khach_de: Int = 0,
    var chot_sodu: Int = 0,
    var tg_loxien: String = "18:13",
    var tg_debc: String = "18:20",
    var loi_donvi: Int = 0,
    var heso_de: Int = 0,
    var maxDe: Int = 0,
    var maxLo: Int = 0,
    var maxXi: Int = 0,
    var maxCang: Int = 0,
    var danDe: String = "",
    var danLo: String = "",
    var soDe: String = "",
    var soLo: String = "",
    var xien2: Int = 0,
    var xien3: Int = 0,
    var xien4: Int = 0,
    var cang: Int = 0,
)