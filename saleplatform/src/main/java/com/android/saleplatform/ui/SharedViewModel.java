/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.saleplatform.ui;

import androidx.lifecycle.ViewModel;

import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.AddressWithUserInfos;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * TODO tip 1：event-ViewModel 的职责仅限于在 "跨页面通信" 的场景下，承担 "唯一可信源"，
 * 所有跨页面的 "状态同步请求" 都交由该可信源在内部决策和处理，并统一分发给所有订阅者页面。
 * <p>
 * 如果这样说还不理解的话，详见《LiveData 鲜为人知的 身世背景 和 独特使命》中结合实际场合 对"唯一可信源"本质的解析。
 * https://xiaozhuanlan.com/topic/0168753249
 *
 * <p>
 * Create by KunMinX at 19/10/16
 */
public class SharedViewModel extends ViewModel {
//    public UnPeekLiveData<GoodsInfo> currentGoodsInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<Integer> searchType = new UnPeekLiveData<>();
    public UnPeekLiveData<String> currentKey = new UnPeekLiveData<>();
    public UnPeekLiveData<AddressInfo> currentAddressInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<AddressWithUserInfos> currentAddressWithUserInfosInfo = new UnPeekLiveData<>();

    public UnPeekLiveData<GoodsInfo> currentMaintainAddedGoodsInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<AddressInfo> currentMaintainAddedAddressInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<AddressWithUserInfos> currentMaintainAddressWithUserInfosInfo = new UnPeekLiveData<>();

    public UnPeekLiveData<Boolean> isAddOrModifyUser = new UnPeekLiveData<>();
    public UnPeekLiveData<Boolean> isAddOrModifiedGoods = new UnPeekLiveData<>();
    public UnPeekLiveData<Boolean> isAddOrModifiedAddress = new UnPeekLiveData<>();

    public UnPeekLiveData<UserInfo> currentModifyUserInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<AddressInfo> currentModifyAddressInfo = new UnPeekLiveData<>();
    public UnPeekLiveData<GoodsInfo> currentModifyGoodsInfo = new UnPeekLiveData<>();


}
