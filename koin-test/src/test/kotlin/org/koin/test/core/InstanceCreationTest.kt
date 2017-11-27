package org.koin.test.core

import org.junit.Assert
import org.junit.Test
import org.koin.core.scope.Scope
import org.koin.dsl.module.Module
import org.koin.standalone.startKoin
import org.koin.test.KoinTest
import org.koin.test.ext.junit.assertContexts
import org.koin.test.ext.junit.assertDefinedInScope
import org.koin.test.ext.junit.assertDefinitions
import org.koin.test.ext.junit.assertRemainingInstances
import org.koin.test.get

class InstanceCreationTest : KoinTest {

    class FlatModule : Module() {
        override fun context() =
                applicationContext {
                    provide { ComponentA() }
                    provide { ComponentB(get()) }
                    provide { ComponentC(get(), get()) }
                }
    }

    class HierarchicModule : Module() {
        override fun context() =
                applicationContext {
                    provide { ComponentA() }

                    context("B") {
                        provide { ComponentB(get()) }

                        context("C") {
                            provide { ComponentC(get(), get()) }
                        }
                    }
                }
    }

    class ComponentA
    class ComponentB(val componentA: ComponentA)
    class ComponentC(val componentB: ComponentB, val componentA: ComponentA)

    @Test
    fun `load and create instances for flat module`() {
        startKoin(listOf(FlatModule()))

        val a = get<ComponentA>()
        val b = get<ComponentB>()
        val c = get<ComponentC>()

        Assert.assertNotNull(a)
        Assert.assertNotNull(b)
        Assert.assertNotNull(c)
        Assert.assertEquals(a, b.componentA)
        Assert.assertEquals(a, c.componentA)
        Assert.assertEquals(b, c.componentB)

        assertRemainingInstances(3)
        assertDefinitions(3)
        assertContexts(1)
        assertDefinedInScope(ComponentA::class, Scope.ROOT)
        assertDefinedInScope(ComponentB::class, Scope.ROOT)
        assertDefinedInScope(ComponentC::class, Scope.ROOT)
    }

    @Test
    fun `load and create instances for hierarchic context`() {
        startKoin(listOf(HierarchicModule()))

        val a = get<ComponentA>()
        val b = get<ComponentB>()
        val c = get<ComponentC>()

        Assert.assertNotNull(a)
        Assert.assertNotNull(b)
        Assert.assertNotNull(c)
        Assert.assertEquals(a, b.componentA)
        Assert.assertEquals(a, c.componentA)
        Assert.assertEquals(b, c.componentB)

        assertRemainingInstances(3)
        assertDefinitions(3)
        assertContexts(3)
        assertDefinedInScope(ComponentA::class, Scope.ROOT)
        assertDefinedInScope(ComponentB::class, "B")
        assertDefinedInScope(ComponentC::class, "C")
    }

}