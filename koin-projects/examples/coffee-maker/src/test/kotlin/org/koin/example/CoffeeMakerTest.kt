package org.koin.example

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class CoffeeMakerTest : AutoCloseKoinTest() {

    val coffeeMaker: CoffeeMaker by inject()
    val heater: Heater by inject()

    @Before
    fun before() {
        startKoin(listOf(coffeeAppModule))
        declareMock<Heater> {
            given(isHot()).will { true }
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testHeaterIsTurnedOnAndThenOff() {
        coffeeMaker.brew()

        verify(heater, times(1)).on()
        verify(heater, times(1)).off()
    }
}