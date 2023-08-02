package com.android.saleplatform.ui.adapter

import android.view.View
import android.widget.TextView
import com.android.arch.data.repository.UserDataRepository
import com.android.arch.domain.example.repository.UserRepository
import com.android.saleplatform.R
import com.android.saleplatform.beans.AddressInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddressAdapter : BaseQuickAdapter<AddressInfo, BaseViewHolder> {
    private var userDataRepository: UserDataRepository? = null

    @Inject
    constructor(userRepository: UserRepository) : super(R.layout.layout_address_item) {
        this.userDataRepository = userRepository as UserDataRepository
    }

    override fun convert(holder: BaseViewHolder, item: AddressInfo) {
        holder.getView<TextView>(R.id.name).text = item.addressName
        item.addressId?.let {
            userDataRepository?.getUserDetailByAddressIdOrGoodsId(it, -1)
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