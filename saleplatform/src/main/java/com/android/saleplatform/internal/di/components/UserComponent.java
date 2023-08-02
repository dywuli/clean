/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.saleplatform.internal.di.components;


import com.android.saleplatform.internal.di.PerActivity;
import com.android.saleplatform.internal.di.modules.ActivityModule;
import com.android.saleplatform.internal.di.modules.UserModule;
import com.android.saleplatform.ui.home.HomeFragment;
import com.android.saleplatform.ui.home.UserInfoFragment;
import com.android.saleplatform.ui.infos.AddUserInfoFragment;
import com.android.saleplatform.ui.infos.AddressListFragment;
import com.android.saleplatform.ui.infos.GoodsListFragment;
import com.android.saleplatform.ui.infos.InfoFragment;
import com.android.saleplatform.ui.order.OrderFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(HomeFragment homeFragment);
  void inject(InfoFragment infoFragment);
  void inject(OrderFragment orderFragment);
  void inject(UserInfoFragment userInfoFragment);
  void inject(AddUserInfoFragment userInfoFragment);
  void inject(GoodsListFragment userInfoFragment);
  void inject(AddressListFragment userInfoFragment);
}
