/*
 * Copyright 2017-2018 the original author or authors.
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
package org.koin.ktor.ext

import io.ktor.application.Application
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.scope.ScopeInstance
import org.koin.dsl.KoinAppDeclaration

/**
 * Ktor Koin extensions
 *
 * @author Arnaud Giuliani
 * @author Laurent Baresse
 */

/**
 * Help start Koin cntofor Ktor
 */
fun Application.installKoin(
    koinApplication: KoinApplication
) {
    startKoin(koinApplication)
}

/**
 * Help start Koin cntofor Ktor
 */
fun Application.installKoin(
    koinApplication: KoinAppDeclaration
) {
    startKoin(koinApplication)
}

/**
 * Help work on ModuleDefinition
 */
fun Application.getKoin(): Koin = GlobalContext.get().koin

/**
 * inject lazily given dependency
 * @param name - bean name / optional
 * @param scope
 * @param parameters
 */
inline fun <reified T : Any> Application.inject(
    name: String = "",
    scope: ScopeInstance? = null,
    noinline parameters: ParametersDefinition? = null
) =
    lazy { get<T>(name, scope, parameters) }

/**
 * Retrieve given dependency for KoinComponent
 * @param name - bean name / optional
 * @param scope
 * @param parameters
 */
inline fun <reified T : Any> Application.get(
    name: String = "",
    scope: ScopeInstance? = null,
    noinline parameters: ParametersDefinition? = null
) =
    getKoin().get<T>(name, scope, parameters)

/**
 * Retrieve given property for KoinComponent
 * @param key - key property
 * throw MissingPropertyException if property is not found
 */
inline fun <reified T> Application.getProperty(key: String) =
    getKoin().getProperty<T>(key)

/**
 * Retrieve given property for KoinComponent
 * give a default value if property is missing
 *
 * @param key - key property
 * @param defaultValue - default value if property is missing
 *
 */
inline fun <reified T> Application.getProperty(key: String, defaultValue: T) =
    getKoin().getProperty(key) ?: defaultValue