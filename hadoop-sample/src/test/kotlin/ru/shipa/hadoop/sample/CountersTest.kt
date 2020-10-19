package ru.shipa.hadoop.sample

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mrunit.mapreduce.MapDriver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.shipa.hadoop.sample.utils.CustomCounterType
import kotlin.test.junit.JUnitAsserter.assertEquals

/**
 * Tests verifying the correct counting of custom counters
 *
 * @author v.shipugin
 */
class CountersTest {

    companion object {
        private val ASCII_REGEX = Regex(pattern = "\\A\\p{ASCII}*\\z")
    }

    private lateinit var mapDriver: MapDriver<LongWritable, Text, IntWritable, Text>

    @BeforeEach
    fun setUp() {
        mapDriver = MapDriver.newMapDriver(Mapper())
    }

    @ParameterizedTest
    @CsvSource(
        "/testInputFiles/error_text_all_5_errors_4.txt,5,4",
        "/testInputFiles/error_text_all_10_errors_1.txt,10,1",
        "/testInputFiles/error_text_all_7_errors_7.txt,7,7",
        "/testInputFiles/valid_text_all_16_errors_0.txt,16,0"
    )
    fun `when map stage finished with counters - should return correct counters value`(
        fileName: String,
        expectedTotalWords: Long,
        expectedMalformedWords: Long
    ) {

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

        val totalWordsCounter = mapDriver.counters.findCounter(CustomCounterType.TOTAL_WORDS)
        val malformedWordsCounter = mapDriver.counters.findCounter(CustomCounterType.MALFORMED_WORDS)

        assertEquals(
            message = "Expected total words counter value",
            expected = expectedTotalWords,
            actual = totalWordsCounter.value
        )

        assertEquals(
            message = "Expected malformed words counter value",
            expected = expectedMalformedWords,
            actual = malformedWordsCounter.value
        )
    }
}
