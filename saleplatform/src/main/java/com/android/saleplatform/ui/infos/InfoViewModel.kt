package com.android.saleplatform.ui.infos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.saleplatform.beans.AddressWithUserInfos
import com.android.saleplatform.beans.GoodsInfo

class InfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    val goodsInfos = MutableLiveData<List<GoodsInfo>>()
    val addressInfoWithUsersList = MutableLiveData<List<AddressWithUserInfos>>()
}