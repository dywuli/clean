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
package com.android.saleplatform.presenter;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public abstract class Presenter<T> {
  public T viewModel;
  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onResume() method.
   */
  public abstract void resume();

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onPause() method.
   */
  public abstract void pause();

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onDestroy() method.
   */
  public abstract void destroy();

  public abstract void setViewModel(T viewModel);
}
