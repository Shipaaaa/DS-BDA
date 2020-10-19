package ru.shipa.hadoop.sample

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mrunit.mapreduce.MapDriver
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * Tests that check the operation of the application
 *
 * @author v.shipugin
 */
class AppTest {

    companion object {
        private val ASCII_REGEX = Regex(pattern = "\\A\\p{ASCII}*\\z")
    }

    private lateinit var mapDriver: MapDriver<LongWritable, Text, IntWritable, Text>
    private lateinit var reduceDriver: ReduceDriver<IntWritable, Text, IntWritable, Text>

    private lateinit var mapReduceDriver: MapReduceDriver<LongWritable, Text, IntWritable, Text, IntWritable, Text>

    @BeforeEach
    fun setUp() {
        val mapper = Mapper()
        val reducer = Reducer()

        mapDriver = MapDriver.newMapDriver(mapper)
        reduceDriver = ReduceDriver.newReduceDriver(reducer)
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "/testInputFiles/error_text_all_5_errors_4.txt",
            "/testInputFiles/error_text_all_10_errors_1.txt",
            "/testInputFiles/error_text_all_7_errors_7.txt",
            "/testInputFiles/valid_text_all_16_errors_0.txt"
        ]
    )
    fun `when map stage finished - should return correct result`(fileName: String) {

        val words = CountersTest::class.java.getResource(fileName).readText()
        val wordsList = words.split("\\s".toRegex())

        mapDriver
            .withInput(LongWritable(wordsList.size.toLong()), Text(words)).apply {
                wordsList.forEach { word ->
                    if (word.matches(ASCII_REGEX)) {
                        withOutput(IntWritable(word.length), Text(word))
                    }
                }
            }
            .runTest()
    }

    @Test
    fun `when reduce stage finished - should return correct result`() {

        val words = listOf("undisliked", "gametocyte", "unissuable")
            .map { Text(it) }

        reduceDriver
            .withInput(IntWritable(10), words)
            .withOutput(IntWritable(10), Text("undisliked gametocyte unissuable"))
            .runTest()
    }

    @Test
    fun `when app finished - should return correct result`() {
        val words = CountersTest::class.java.getResource("/testInputFiles/map_reduce_test.txt").readText()

        mapReduceDriver
            .withInput(LongWritable(), Text(words))
            .withOutput(IntWritable(8), Text("fermerer"))
            .withOutput(IntWritable(9), Text("mortarize"))
            .withOutput(IntWritable(10), Text("undisliked gametocyte unissuable"))
            .withOutput(IntWritable(12), Text("unaffrighted"))
            .withOutput(IntWritable(13), Text("overbashfully paleostriatal"))
            .withOutput(IntWritable(15), Text("precapitalistic photometrograph"))
            .runTest()
    }
}