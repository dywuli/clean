package com.android.saleplatform.beans

import java.sql.Date


class GoodsInfo {
    var goodsName: String? = ""
    var price: Double? = 0.0
    var goodsId: Long? = -1
    var createDate: String? = null
    var updateDate: String? = null

    constructor(
        goodName: String?,
        price: Double?,
        goodsId: Long?,
        createDate: String?,
        updateDate: String?
    ) {
        this.goodsName = goodName
        this.price = price
        this.goodsId = goodsId
        this.createDate = createDate
        this.updateDate = updateDate
    }

    override fun toString(): String {
        return goodsName!! + " NO:" + goodsId;
    }

}