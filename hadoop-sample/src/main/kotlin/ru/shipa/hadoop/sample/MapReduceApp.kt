package ru.shipa.hadoop.sample

class MapReduceApp {
    val greeting: String
        get() {
            return "Hello world."
        }
}

fun main(args: Array<String>) {
    println(MapReduceApp().greeting)
}
