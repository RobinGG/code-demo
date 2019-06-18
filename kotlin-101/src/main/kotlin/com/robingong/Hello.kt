package com.robingong

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong

fun main(args: Array<String>) {
    println("Start")
    GlobalScope.launch {
        delay(1000)
        println("Hello, World")
    }

    runBlocking {
        delay(2000)
    }

    printSum()
    printSumAsync()

    println("Stop")
}

fun printSum() {
    val c = AtomicLong()
    for (i in 1..1_000_000L)
        GlobalScope.launch {
            c.addAndGet(i)
        }
    println(c.get())
}

fun printSumAsync() {
    val c = AtomicLong()
    val deferred = (1..1_000_000).map { n -> GlobalScope.async { n } }
    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }
}
