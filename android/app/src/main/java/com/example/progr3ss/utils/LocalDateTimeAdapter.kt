package com.example.progr3ss.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    override fun deserialize(
        json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?
    ): LocalDateTime {
        val s = json.asString
        return try {
            Instant.parse(s).atZone(ZoneId.systemDefault()).toLocalDateTime()
        } catch (e: Exception) {
            LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)
        }
    }

    override fun serialize(
        src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(
            src?.atZone(ZoneId.systemDefault())?.toInstant()?.toString()
        )
    }
}