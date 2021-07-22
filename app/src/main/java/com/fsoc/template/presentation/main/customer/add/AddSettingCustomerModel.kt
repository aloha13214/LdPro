package com.fsoc.template.presentation.main.customer.add

data class AddSettingCustomerModel(
    val typePrize: String,
    var valuePrize: Float? = 0.72F,
    var counter: Float? = 70F
)