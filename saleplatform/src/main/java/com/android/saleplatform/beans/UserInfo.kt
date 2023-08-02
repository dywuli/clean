package com.android.saleplatform.beans


class UserInfo {
    var id: Long? = -1
    var goodsId: Long? = -1
    var addressId: Long? = -1
    var goodsInfo: GoodsInfo? = null
    var addressInfo: AddressInfo? = null
    var userName: String? = ""
    var roomAddress: String? = ""
    var count: Int = 0
    var payStatus = 0 //0为未付;1已付
    var takeStatus = 0 //0为未送;1已送
    var completedStatus = 0 //0为未;1已
    var createDate: String? = null
    var updateDate: String? = null
    var totalMoney: String? = ""
    var desc: String? = ""

    constructor(
        id: Long?,
        goodsInfo: GoodsInfo?,
        addressInfo: AddressInfo?,
        userName: String?,
        roomAddress: String?,
        count: Int,
        payStatus: Int,
        takeStatus: Int,
        completedStatus: Int,
        createDate: String?,
        updateDate: String?,
        totalMoney: String?,
        desc: String?
    ) {
        this.id = id
        this.goodsInfo = goodsInfo
        this.addressInfo = addressInfo
        this.userName = userName
        this.roomAddress = roomAddress
        this.count = count
        this.payStatus = payStatus
        this.takeStatus = takeStatus
        this.completedStatus = completedStatus
        this.createDate = createDate;
        this.updateDate = updateDate
        this.desc = desc
        this.totalMoney = totalMoney
    }

    constructor(
        id: Long?,
        goodsId: Long?,
        addressId: Long?,
        goodsInfo: GoodsInfo?,
        addressInfo: AddressInfo?,
        userName: String?,
        roomAddress: String?,
        count: Int,
        payStatus: Int,
        takeStatus: Int,
        completedStatus: Int,
        createDate: String?,
        updateDate: String?,
        totalMoney: String?,
        desc: String?
    ) {
        this.id = id
        this.goodsId = goodsId
        this.addressId = addressId
        this.goodsInfo = goodsInfo
        this.addressInfo = addressInfo
        this.userName = userName
        this.roomAddress = roomAddress
        this.count = count
        this.payStatus = payStatus
        this.takeStatus = takeStatus
        this.completedStatus = completedStatus
        this.createDate = createDate
        this.updateDate = updateDate
        this.totalMoney = totalMoney
        this.desc = desc
    }

}