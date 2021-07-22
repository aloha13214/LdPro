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
    val dlgiu_de: Int = 0,
    val dlgiu_lo: Int = 0,
    val dlgiu_xi: Int = 0,
    val dlgiu_xn: Int = 0,
    val dlgiu_bc: Int = 0,
    val khgiu_de: Int = 0,
    val khgiu_lo: Int = 0,
    val khgiu_xi: Int = 0,
    val khgiu_xn: Int = 0,
    val khgiu_bc: Int = 0,
    val ok_tin: Int = 0,
    val xien_nhan: Int = 0,
    val chot_sodu: Int = 0,
    val tg_loxien: String = "18:13",
    val tg_debc: String = "18:20",
    val loi_donvi: Int = 0,
    val heso_de: Int = 0,
    val maxDe: Int = 0,
    val maxLo: Int = 0,
    val maxXi: Int = 0,
    val maxCang: Int = 0,
    val danDe: String = "",
    val danLo: String = "",
    val soDe: String = "",
    val soLo: String = "",
    val xien2: Int = 0,
    val xien3: Int = 0,
    val xien4: Int = 0,
    val cang: Int = 0,
)