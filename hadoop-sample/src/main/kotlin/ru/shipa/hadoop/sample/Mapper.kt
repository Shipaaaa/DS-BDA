package ru.shipa.hadoop.sample

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper
import org.slf4j.LoggerFactory
import ru.shipa.hadoop.sample.utils.CustomCounterType
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

/**
 * Mapper: parses a word from the input file, assigns its length to each word
 *
 * Input data:
 *  key - word count
 *  value - text
 *
 * Output data:
 *  key - word length
 *  value - word
 *
 * @author v.shipugin
 */
class Mapper : Mapper<LongWritable, Text, IntWritable, Text>() {

    companion object {
        private val logger = LoggerFactory.getLogger(Mapper::class.java)

        /**
         * Regex to check a word for only ASCII characters
         */
        private val ASCII_REGEX = Regex(pattern = "\\A\\p{ASCII}*\\z")

        private val mapKey = IntWritable()
        private val mapValue = Text()
    }

    @Throws(IOException::class, InterruptedException::class)
    override fun map(key: LongWritable, value: Text, context: Context) {

        val tokenizer = StringTokenizer(value.toString())

        while (tokenizer.hasMoreTokens()) {

            val word = tokenizer.nextToken()
            context.getCounter(CustomCounterType.TOTAL_WORDS).increment(1)

            if (!word.matches(ASCII_REGEX)) {
                logger.error("Word $word contains not non ascii character")
                context.getCounter(CustomCounterType.MALFORMED_WORDS).increment(1)
                continue
            }

            mapKey.set(word.length)
            mapValue.set(word)

            context.write(mapKey, mapValue)
        }
    }
}