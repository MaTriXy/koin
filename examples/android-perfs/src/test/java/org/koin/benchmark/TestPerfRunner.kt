package org.koin.benchmark

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestPerfRunner {

    /**
     * Avg start time: 13.06
     * Avg execution time: 0.158
     */
    @Test
    fun main() = runBlocking {
//        val limits = PerfLimit(10.0, 0.1)
//        println("Perf target: $limits")

        val results = PerfRunner.runAll(useDebugLogs = false, module = ::perfModule400)
        println("Perfs results: $results")
//        results.applyLimits(limits)
    }

    @Test
    fun main_Fu() = runBlocking {
//        val limits = PerfLimit(10.0, 0.1)
//        println("Perf target: $limits")

        val results = PerfRunner.runAll(useDebugLogs = false, module = ::perfModule400_Fu)
        println("Perfs results: $results")
//        results.applyLimits(limits)
    }

}