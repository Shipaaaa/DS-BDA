package ru.shipa.elasticsearch.sample.entity

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.message.FormattedMessageFactory
import org.apache.logging.log4j.message.Message

class SerializedMessageFactory(private val objectMapper: ObjectMapper) : FormattedMessageFactory() {

    override fun newMessage(message: Any?): Message = SerializedMessage(message, objectMapper)
}