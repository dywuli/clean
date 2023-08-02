package com.android.saleplatform.ui.adapter

import android.view.View
import android.widget.TextView
import com.android.arch.data.repository.UserDataRepository
import com.android.arch.domain.example.repository.UserRepository
import com.android.saleplatform.R
import com.android.saleplatform.beans.GoodsInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoodsAdapter1 :
    BaseQuickAdapter<GoodsInfo, BaseViewHolder> {
    private var userDataRepository: UserDataRepository? = null

    @Inject
    constructor(userRepository: UserRepository) : super(R.layout.layout_good_item_01) {
        this.userDataRepository = userRepository as UserDataRepository
    }

    override fun convert(holder: BaseViewHolder, item: GoodsInfo) {
        holder.getView<TextView>(R.id.name).text = item.goodsName
        item.goodsId?.let {
            userDataRepository?.getUserDetailByAddressIdOrGoodsId(-1, it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { t1, t2 ->
                    if (t1 != null && !t1.isEmpty()) {
                        holder.getView<View>(R.id.btn_del).alpha = 0.3f
                    } else {
                        holder.getView<View>(R.id.btn_del).alpha = 1.0f
                    }
                }
        }
    }

}