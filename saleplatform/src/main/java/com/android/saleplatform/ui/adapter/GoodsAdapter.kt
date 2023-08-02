package com.android.saleplatform.ui.adapter

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.android.saleplatform.R
import com.android.saleplatform.beans.AddressInfo
import com.android.saleplatform.beans.AddressWithUserInfos
import com.android.saleplatform.domain.interactor.GetUserListByAddressId
import com.android.saleplatform.domain.interactor.GetUserListByAddressIdGoodsId
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import javax.inject.Inject

class GoodsAdapter : BaseQuickAdapter<AddressWithUserInfos, BaseViewHolder> {

    @Inject
    constructor(getUserListByAddressId: GetUserListByAddressId) : super(R.layout.layout_good_item) {
        this.getUserListByAddressIdGoodsId = getUserListByAddressIdGoodsId
    }

    var getUserListByAddressIdGoodsId: GetUserListByAddressId? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun convert(holder: BaseViewHolder, item: AddressWithUserInfos) {
        holder.getView<TextView>(R.id.address_name).text = (item.addressInfo.addressName)
//        holder.getView<TextView>(R.id.good_name).text = (item.addressInfo.goodsInfo?.goodsName)
//        holder.getView<TextView>(R.id.good_name).visibility = View.GONE

//        if (item.userInfos == null || item.userInfos!!.isEmpty()) {
//            getUserListByAddressIdGoodsId?.execute(
//                object : DefaultObserver<List<UserEntity>>() {
//                    @RequiresApi(Build.VERSION_CODES.N)
//                    override fun onNext(t: List<UserEntity>) {
//                        holder.getView<TextView>(R.id.goods_count).text = t.size.toString()
//                        holder.getView<TextView>(R.id.goods_count_remain).text =
//                            t?.stream()?.filter { it.completedStatus == 0 }?.count().toString()
//                    }
//                },
//                GetUserListByAddressId.Param(
//                    item.addressInfo.addressId,
//                    item.addressInfo.goodsInfo?.goodsId!!
//                )
//            )
//            Log.e("wuli", " " +item.addressInfo.addressId + " " + item.addressInfo.goodsInfo?.goodsId)
//        }
        holder.getView<TextView>(R.id.goods_count).text = item.userInfos?.size.toString()
        holder.getView<TextView>(R.id.goods_count_remain).text =
            item.userInfos?.stream()?.filter { it.completedStatus == 0 }?.count().toString()
    }

}