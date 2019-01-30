package org.koin.core.definition

import org.koin.core.Koin
import org.koin.core.error.MissingPropertyException
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.scope.ScopeInstance

sealed class DefinitionContext(val koin: Koin) {

    abstract fun getCurrentScope(): ScopeInstance?

    /**
     * Resolve an instance from Koin
     * @param name
     * @param scope
     * @param parameters
     */
    inline fun <reified T> get(
            name: String? = null,
            noinline parameters: ParametersDefinition? = null
    ): T {
        return koin.get(name, getCurrentScope(), parameters)
    }

    /**
     * Resolve an instance from Koin / extenral scope instance
     * @param name
     * @param scope
     * @param parameters
     */
    inline fun <reified T> get(
            name: String? = null,
            scope: ScopeInstance,
            noinline parameters: ParametersDefinition? = null
    ): T {
        return koin.get(name, scope, parameters)
    }

    /**
     * Get a property from Koin
     * @param key
     */
    fun <T> getProperty(key: String): T {
        return koin.getProperty(key)
                ?: throw MissingPropertyException("Property '$key' is missing")
    }
}

class DefaultContext(koin: Koin) : DefinitionContext(koin) {
    override fun getCurrentScope(): ScopeInstance? = null
}

class ScopedContext(koin: Koin, val scopeInstance: ScopeInstance) : DefinitionContext(koin) {
    override fun getCurrentScope(): ScopeInstance? = scopeInstance
}