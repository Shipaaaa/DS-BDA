package ru.shipa.hadoop.sample

import kotlin.test.Test
import kotlin.test.assertNotNull

class MapReduceAppTest {
    @Test fun testAppHasAGreeting() {
        val classUnderTest = MapReduceApp()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
    }
}
