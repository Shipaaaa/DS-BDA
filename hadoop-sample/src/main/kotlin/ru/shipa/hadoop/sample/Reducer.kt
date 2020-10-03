package ru.shipa.hadoop.sample

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

/**
 * Reducer: Concatenates all words of the same length into one string
 *
 * Input data:
 *  key - word length
 *  value - word
 *
 * Output data:
 *  key - word length
 *  value - found words combined with a space
 *
 * @author v.shipugin
 */
class Reducer : Reducer<IntWritable, Text, IntWritable, Text>() {

    @Throws(IOException::class, InterruptedException::class)
    override fun reduce(wordLength: IntWritable, words: Iterable<Text>, context: Context) {
        val foundedWords = LinkedList<String>()

        for (word in words) foundedWords.add(word.toString())

        context.write(wordLength, Text(foundedWords.joinToString(separator = " ")))
    }
}