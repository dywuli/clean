package com.android.saleplatform.presenter;

import com.android.arch.data.repository.UserDataRepository;
import com.android.arch.domain.example.repository.UserRepository;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.domain.interactor.GetUserListByStatus;
import com.android.saleplatform.internal.di.PerActivity;
import com.android.saleplatform.mapper.UserModelDataMapper;
import com.android.saleplatform.ui.order.OrderViewModel;

import javax.inject.Inject;

/**
 * @author wuli
 */
@PerActivity
public class OrderPresenter extends Presenter<OrderViewModel> {
    private final UserModelDataMapper userModelDataMapper;

    private final GetUserListByStatus userListByStatus;

    private UserDataRepository userDataRepository;

    @Inject
    public OrderPresenter(UserModelDataMapper userModelDataMapper, GetUserListByStatus userListByStatus, UserRepository userDataRepository) {
        this.userModelDataMapper = userModelDataMapper;
        this.userListByStatus = userListByStatus;
        this.userDataRepository = (UserDataRepository) userDataRepository;
    }


    public long insertGoods(GoodsInfo goodsInfo) {
        return userDataRepository.insertGoods(userModelDataMapper.GoodInfo2GoodEntity(goodsInfo));
    }

    public long insertAddress(AddressInfo addressInfo) {
        return userDataRepository.insertAddress(userModelDataMapper.AddressInfo2AddressEntity(addressInfo));
    }

    public long insertUserInfo(UserInfo userInfo) {
        return userDataRepository.insertUser(userModelDataMapper.UserInfo2Entity(userInfo));
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setViewModel(OrderViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
