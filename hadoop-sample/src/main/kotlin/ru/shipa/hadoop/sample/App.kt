package ru.shipa.hadoop.sample

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
import ru.shipa.hadoop.sample.utils.CustomCounterType
import ru.shipa.hadoop.sample.utils.DescendingKeyComparator
import kotlin.system.exitProcess

const val JOB_NAME = "Longest word search app"

const val SUCCESS_STATUS = 0
const val ERROR_STATUS = 1

/**
 * Application entry point. Starts a map-reduce operation.
 *
 * @author v.shipugin
 */
fun main(args: Array<String>) {

    val conf = Configuration().apply {
        set(TextOutputFormat.SEPERATOR, ",")
    }

    val inputDir = Path(args[0])
    val outputDir = Path(args[1])

    val job = Job(conf, JOB_NAME).apply {
        mapperClass = Mapper::class.java
        reducerClass = Reducer::class.java

        // Redefining the comparator to get the sorting in reverse order.
        setSortComparatorClass(DescendingKeyComparator::class.java)

        inputFormatClass = TextInputFormat::class.java
        outputFormatClass = TextOutputFormat::class.java

        outputKeyClass = IntWritable::class.java
        outputValueClass = Text::class.java
    }

    val hdfs = FileSystem.get(conf)
    // Removing the result directory if it exists
    if (hdfs.exists(outputDir)) hdfs.delete(outputDir, true)

    FileInputFormat.addInputPath(job, inputDir)
    FileOutputFormat.setOutputPath(job, outputDir)

    val status = if (job.waitForCompletion(true)) {
        println("Total amount of words : " + job.counters.findCounter(CustomCounterType.TOTAL_WORDS).value)
        println("Amount of malformed words : " + job.counters.findCounter(CustomCounterType.MALFORMED_WORDS).value)
        SUCCESS_STATUS
    } else {
        ERROR_STATUS
    }

    exitProcess(status)
}


