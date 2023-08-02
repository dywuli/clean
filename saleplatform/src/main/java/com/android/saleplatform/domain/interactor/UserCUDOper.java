package com.android.saleplatform.domain.interactor;

import com.android.arch.data.room.dao.UserDao;
import com.android.arch.data.room.entity.UserEntity;
import com.android.arch.domain.executor.PostExecutionThread;
import com.android.arch.domain.executor.ThreadExecutor;
import com.android.arch.domain.interactor.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

/**
 * @author wuli
 */
public class UserCUDOper extends UseCase<Long, UserCUDOper.Param> {
    private UserDao userDao;
    public static final int TYPE_INSERT = 0;
    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_DEL = 2;

    @Inject
    protected UserCUDOper(UserDao userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userDao = userRepository;
    }

    @Override
    protected Observable<Long> buildUseCaseObservable(final Param param) {
        switch (param.type) {
            case TYPE_UPDATE:
                return Observable.create((ObservableOnSubscribe<Long>) e -> {
                    userDao.updateUser(param.userEntity);
                    e.onNext(1L);
                    e.onComplete();
                });

            case TYPE_DEL:
                return Observable.create((ObservableOnSubscribe<Long>) e -> {
                    userDao.deleteUser(param.userEntity);
                    e.onNext(1L);
                    e.onComplete();
                });

            case TYPE_INSERT:
            default:
                return Observable.create((ObservableOnSubscribe<Long>) e -> {
                    long id = userDao.insertUser(param.userEntity);
                    e.onNext(id);
                    e.onComplete();
                });

        }

    }

    public static final class Param {
        private int type = 0;
        private UserEntity userEntity = null;

        public Param(int type, UserEntity userEntity) {
            this.type = type;
            this.userEntity = userEntity;
        }
    }

    public long insert(UserEntity userEntity) {
        long id = userDao.insertUser(userEntity);
        return id;
    }

    public void update(UserEntity entity) {
        userDao.updateUser(entity);
    }

    public void delete(UserEntity userEntity) {
        userDao.deleteUser(userEntity);
    }
}
