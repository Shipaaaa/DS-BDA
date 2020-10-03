package ru.shipa.hadoop.sample.utils

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.WritableComparable
import org.apache.hadoop.io.WritableComparator

/**
 * The comparator of IntWritable keys to get the sorting in reverse order.
 *
 * @author v.shipugin
 */
class DescendingKeyComparator : WritableComparator(IntWritable::class.java, true) {
    override fun compare(valueOne: WritableComparable<*>, valueTwo: WritableComparable<*>?): Int {
        val k1 = valueOne as IntWritable
        val k2 = valueTwo as IntWritable?

        return -1 * k1.compareTo(k2)
    }
}