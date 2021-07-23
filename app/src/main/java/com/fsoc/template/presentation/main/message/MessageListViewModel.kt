package com.fsoc.template.presentation.main.message

import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.presentation.base.BaseViewModel
import javax.inject.Inject

class MessageListViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : BaseViewModel() {

}