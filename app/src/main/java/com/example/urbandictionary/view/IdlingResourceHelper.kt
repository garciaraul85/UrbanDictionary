package com.example.urbandictionary.view

object IdlingResourceHelper {
    private const val RESOURCE = "SMOOTHIE"

    @JvmField
    val countingIdlingResource = IdlingResourceCounter(RESOURCE)

    fun startProcess() {
        countingIdlingResource.increment()
    }

    fun endProcess() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}