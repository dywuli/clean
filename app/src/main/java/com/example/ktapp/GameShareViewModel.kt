package com.example.ktapp

import android.app.Application
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class GameShareViewModel : BaseFragmentSharedVM {
    val TAG = "ChatAndGameUtils"   //这里使用跟 gameutils 一样的tag  两个 都是游戏流程  方便 过滤

    val data:UnPeekLiveData<Long> = UnPeekLiveData()
    constructor(application: Application) : super(application) {
        Observable.interval(3,TimeUnit.SECONDS).subscribe {
            data.postValue(it)
        }
    }

}