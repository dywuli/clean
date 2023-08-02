package com.android.saleplatform.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.saleplatform.beans.AddressInfo
import com.android.saleplatform.beans.AddressWithUserInfos
import com.android.saleplatform.beans.GoodsInfo

class HomeViewModel : ViewModel() {
    val goodsInfos = MutableLiveData<List<GoodsInfo>>()
    val addressInfoWithUsersList = MutableLiveData<List<AddressWithUserInfos>>()
    val addressInfoList = MutableLiveData<List<AddressInfo>>()

}