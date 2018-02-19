package org.koin.test.core

import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test
import org.koin.Koin
import org.koin.core.scope.Scope
import org.koin.dsl.module.applicationContext
import org.koin.log.PrintLogger
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.test.AbstractKoinTest
import org.koin.test.ext.junit.assertContexts
import org.koin.test.ext.junit.assertDefinedInScope
import org.koin.test.ext.junit.assertDefinitions
import org.koin.test.ext.junit.assertRemainingInstances


class AdditionalBindingTest : AbstractKoinTest() {

    val BoundModule = applicationContext {
        provide { ComponentA() } bind InterfaceComponent::class
    }

    val NotBoundModule = applicationContext {
        provide { ComponentA() }
    }

    val GenericBoundModule = applicationContext {
        provide { ComponentB() } bind OtherInterfaceComponent::class
    }

    val TwoBoundModule = applicationContext {
        provide { ComponentB() } bind OtherInterfaceComponent::class
        provide { ComponentC() } bind OtherInterfaceComponent::class
    }

    class ComponentA : InterfaceComponent
    interface InterfaceComponent

    class ComponentB : OtherInterfaceComponent<String> {
        override fun get() = "HELLO"

    }

    class ComponentC : OtherInterfaceComponent<String> {
        override fun get() = "HELLO_C"

    }

    interface OtherInterfaceComponent<T> {
        fun get(): T
    }

    @Test
    fun `same instance for provided & bound component`() {
        startKoin(listOf(BoundModule))

        val a = get<ComponentA>()
        val intf = get<InterfaceComponent>()

        Assert.assertNotNull(a)
        Assert.assertNotNull(intf)
        Assert.assertEquals(a, intf)

        assertRemainingInstances(1)
        assertDefinitions(1)
        assertContexts(1)
        assertDefinedInScope(ComponentA::class, Scope.ROOT)
    }

    @Test
    fun `should not bound component`() {
        startKoin(listOf(NotBoundModule))

        val a = get<ComponentA>()

        try {
            get<InterfaceComponent>()
            fail()
        } catch (e: Exception) {
        }

        Assert.assertNotNull(a)

        assertRemainingInstances(1)
        assertDefinitions(1)
        assertContexts(1)
        assertDefinedInScope(ComponentA::class, Scope.ROOT)
    }

    @Test
    fun `should bind generic component`() {
        startKoin(listOf(GenericBoundModule))

        val b = get<ComponentB>()
        val intf = get<OtherInterfaceComponent<String>>()

        Assert.assertNotNull(b)
        Assert.assertNotNull(intf)
        Assert.assertEquals("HELLO", intf.get())

        assertRemainingInstances(1)
        assertDefinitions(1)
        assertContexts(1)
        assertDefinedInScope(ComponentB::class, Scope.ROOT)
    }

    @Test
    fun `should not bind generic component`() {
        startKoin(listOf(TwoBoundModule))

        try {
            get<OtherInterfaceComponent<String>>()
            fail()
        } catch (e: Exception) {
        }

        assertRemainingInstances(0)
        assertDefinitions(2)
        assertContexts(1)
        assertDefinedInScope(ComponentB::class, Scope.ROOT)
        assertDefinedInScope(ComponentC::class, Scope.ROOT)
    }

}