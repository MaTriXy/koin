package org.koin.sample

import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.spark.SparkController
import org.koin.spark.experimental.builder.controller
import org.koin.spark.start
import spark.kotlin.get

val helloAppModule = module {
    single<HelloService> { create<HelloServiceImpl>() }
    single<HelloRepository> { create<HelloRepositoryImpl>() }
    controller<HelloController>()
}

interface HelloRepository {
    fun getHello(): String
}

class HelloRepositoryImpl : HelloRepository {
    override fun getHello(): String = "Spark & Koin"
}

interface HelloService {
    fun sayHello(): String
}

class HelloServiceImpl(val helloRepository: HelloRepository) : HelloService {
    override fun sayHello() = "Hello ${helloRepository.getHello()} !"
}

class HelloController(val service: HelloService) : SparkController {
    init {
        get("/hello") {
            service.sayHello()
        }
    }
}

fun main(vararg args: String) {
    // Spark with Koin
    start(modules = listOf(helloAppModule))
}