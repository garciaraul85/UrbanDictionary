package com.example.urbandictionary.view

object Smoothie {
    private const val RESOURCE = "SMOOTHIE"

    @JvmField
    val countingIdlingResource = SmoothieThermometer(RESOURCE)

    fun startProcess() {
        countingIdlingResource.increment()
    }

    fun endProcess() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}