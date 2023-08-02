package com.android.saleplatform.domain.interactor;

import com.android.arch.data.room.dao.GoodsDao;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.domain.executor.PostExecutionThread;
import com.android.arch.domain.executor.ThreadExecutor;
import com.android.arch.domain.interactor.UseCase;
import com.android.saleplatform.beans.GoodsInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GoodsListReq extends UseCase<List<GoodsEntity>, Void> {
    private GoodsDao goodsDao;

    @Inject
    GoodsListReq(GoodsDao userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.goodsDao = userRepository;
    }

    @Override
    protected Observable<List<GoodsEntity>> buildUseCaseObservable(Void goodsInfo) {
        return goodsDao.getGoods().toObservable();
    }
}
