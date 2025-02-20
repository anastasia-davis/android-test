/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.test.espresso.device.action

import android.util.Log
import androidx.test.espresso.device.common.executeShellCommand
import androidx.test.espresso.device.common.getMapOfDeviceStateNamesToIdentifiers
import androidx.test.espresso.device.context.ActionContext
import androidx.test.espresso.device.controller.DeviceMode
import androidx.test.platform.device.DeviceController
import androidx.window.layout.FoldingFeature
import java.util.concurrent.Executor

/** Action to set the test device to be completely flat, like a tablet. */
internal class FlatModeAction(private val mainExecutor: Executor) :
  BaseSingleFoldDeviceAction(DeviceMode.FLAT, FoldingFeature.State.FLAT, mainExecutor) {
  override fun perform(context: ActionContext, deviceController: DeviceController) {
    val currentDeviceStateIdentifier = executeShellCommand("cmd device_state print-state").trim()
    if (currentDeviceStateIdentifier == getMapOfDeviceStateNamesToIdentifiers().get("OPENED")) {
      if (Log.isLoggable(TAG, Log.DEBUG)) {
        Log.d(TAG, "Device is already in flat mode.")
      }
      return
    }

    super.perform(context, deviceController)
  }

  companion object {
    private val TAG = FlatModeAction::class.java.simpleName
  }
}
