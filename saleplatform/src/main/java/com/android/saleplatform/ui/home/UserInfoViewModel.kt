package com.android.saleplatform.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.saleplatform.beans.AddressWithUserInfos
import com.android.saleplatform.beans.GoodsInfo
import com.android.saleplatform.beans.UserInfo

class UserInfoViewModel : ViewModel() {
    val userInfos = MutableLiveData<List<UserInfo>>()
    val userName = MutableLiveData<String>()
    val allUses = MutableLiveData<List<UserInfo>>()
}