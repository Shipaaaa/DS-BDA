package ru.shipa.elasticsearch.sample.entity

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.message.ObjectMessage

class SerializedMessage(userLog: Any?, private val objectMapper: ObjectMapper) : ObjectMessage(userLog) {

    private var serializedMessage: String? = null

    override fun getFormattedMessage(): String {
        if (serializedMessage == null) {
            serializedMessage = try {
                objectMapper.writeValueAsString(parameter)
            } catch (e: JsonProcessingException) {
                throw IllegalArgumentException(e.message)
            }
        }
        return serializedMessage!!
    }
}