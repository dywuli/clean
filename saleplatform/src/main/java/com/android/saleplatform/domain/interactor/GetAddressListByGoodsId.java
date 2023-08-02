package com.android.saleplatform.domain.interactor;

import com.android.arch.data.room.dao.GoodsDao;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.domain.executor.PostExecutionThread;
import com.android.arch.domain.executor.ThreadExecutor;
import com.android.arch.domain.interactor.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author wuli
 */
public class GetAddressListByGoodsId extends UseCase<List<GoodsWithAddressEntity>, Long> {
    private GoodsDao goodsDao;

    @Inject
    GetAddressListByGoodsId(GoodsDao userRepository, ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.goodsDao = userRepository;
    }


    @Override
    protected Observable<List<GoodsWithAddressEntity>> buildUseCaseObservable(Long goodsId) {
        return goodsDao.getAddressListByGoodsId(goodsId).toObservable();
    }
}
