package com.android.saleplatform.domain.interactor;

import com.android.arch.data.room.dao.AddressDao;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.domain.executor.PostExecutionThread;
import com.android.arch.domain.executor.ThreadExecutor;
import com.android.arch.domain.interactor.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetUserListGroupByAddrByAddressName extends UseCase<List<AddressWithGoodsEntity>, String> {
    private AddressDao addressDao;

    @Inject
    protected GetUserListGroupByAddrByAddressName(AddressDao userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.addressDao = userRepository;
    }

    @Override
    protected Observable<List<AddressWithGoodsEntity>> buildUseCaseObservable(String integer) {
        return addressDao.getAddressWitGoodsByName(integer).toObservable();
    }
}
