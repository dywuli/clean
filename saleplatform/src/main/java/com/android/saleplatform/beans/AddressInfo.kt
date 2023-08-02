package com.android.saleplatform.beans

import java.sql.Date


class AddressInfo(
    var addressId: Long,
    var addressName: String? = "",
    var goodsInfo: GoodsInfo?,
    var createDate: String?,
    var updateDate: String?
){
    override fun toString(): String {
        return addressName!!
    }
}