package com.fsoc.template.common

import com.fsoc.template.data.db.entity.CustomerEntity

typealias ClickOnCustomer = (Long) -> Unit
typealias ClickDeleteCustomer = (CustomerEntity) -> Unit