package com.android.saleplatform.ui.adapter

import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import com.android.arch.data.repository.UserDataRepository
import com.android.arch.domain.example.repository.UserRepository
import com.android.saleplatform.R
import com.android.saleplatform.beans.UserInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserAdapter : BaseQuickAdapter<UserInfo, BaseViewHolder> {

    private var userDataRepository: UserDataRepository? = null

    @Inject
    constructor(userRepository: UserRepository) : super(R.layout.layout_good_user_info_item) {
        this.userDataRepository = userRepository as UserDataRepository
    }

    override fun convert(holder: BaseViewHolder, item: UserInfo) {
        holder.getView<TextView>(R.id.user_name).text = "用户:" + item.userName

        holder.getView<TextView>(R.id.user_addr).text = "小区:" + item.addressInfo?.addressName
        holder.getView<TextView>(R.id.user_room).text = "详细地址:" + item.roomAddress
        holder.getView<TextView>(R.id.user_desc).text = "备注:" + item.desc
        holder.getView<TextView>(R.id.good_count).text = "总金额:" + item.count.toString()
        holder.getView<TextView>(R.id.good_status_payed).text =
            if (item.payStatus == 1) context.getString(R.string.payed) else context.getString(R.string.no_payed)
        holder.getView<TextView>(R.id.good_status_take).text =
            if (item.takeStatus == 1) context.getString(R.string.taken) else context.getString(R.string.no_taken)
        holder.getView<TextView>(R.id.good_status_completed).text =
            if (item.completedStatus == 1) context.getString(R.string.completed) else context.getString(
                R.string.uncompleted
            )

        holder.getView<TextView>(R.id.good_status_completed).background =
            if (item.completedStatus == 1) context.getDrawable(R.drawable.bg_line) else context.getDrawable(
                R.drawable.bg_red_line
            )

        userDataRepository?.getGoodsById(item.goodsId!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe { t1, t2 ->
                if (t1 != null) {
                    holder.getView<TextView>(R.id.user_good).text = "商品:" + t1.goodsName
                } else {
                    holder.getView<TextView>(R.id.user_good).text = "商品:null"
                }
                if (t2 != null) {
                    Log.e("wuli", "***" + t2)
                }
            }

        userDataRepository?.getAddressById(item.addressId!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe { t1, t2 ->
                if (t1 != null) {
                    holder.getView<TextView>(R.id.user_addr).text = "小区:" + t1.addressName
                } else {
                    holder.getView<TextView>(R.id.user_addr).text = "小区:null"
                }
                if (t2 != null) {
                    Log.e("wuli", "***" + t2)
                }
            }
    }

}