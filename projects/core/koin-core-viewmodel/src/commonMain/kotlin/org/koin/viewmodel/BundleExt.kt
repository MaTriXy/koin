/*
 * Copyright 2017-Present the original author or authors.
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
package org.koin.viewmodel

import androidx.core.bundle.Bundle
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.savedstate.SavedStateRegistryOwner
import org.koin.core.annotation.KoinInternalApi

/**
 * Scope extensions to help for ViewModel
 *
 * @author Arnaud Giuliani
 */

/**
 * Convert current Bundle to CreationExtras
 * @param viewModelStoreOwner
 */
@KoinInternalApi
fun Bundle.toExtras(viewModelStoreOwner: ViewModelStoreOwner): CreationExtras? {
    return if (keySet().isEmpty()) null
    else {
        runCatching {
            MutableCreationExtras().also { extras ->
                extras[DEFAULT_ARGS_KEY] = this
                extras[VIEW_MODEL_STORE_OWNER_KEY] = viewModelStoreOwner
                extras[SAVED_STATE_REGISTRY_OWNER_KEY] = viewModelStoreOwner as SavedStateRegistryOwner
            }
        }.getOrNull()
    }
}

//TODO Replace with CreationExtras API
fun emptyState(): BundleDefinition = { Bundle() }

//TODO Replace with CreationExtras API
typealias BundleDefinition = () -> Bundle