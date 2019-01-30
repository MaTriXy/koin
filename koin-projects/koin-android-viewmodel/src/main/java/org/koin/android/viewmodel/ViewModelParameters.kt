package org.koin.android.viewmodel

import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

class ViewModelParameters<T : Any>(
    val clazz: KClass<T>,
    val name: String? = null,
    val from: ViewModelStoreOwnerDefinition? = null,
    val parameters: ParametersDefinition? = null
)