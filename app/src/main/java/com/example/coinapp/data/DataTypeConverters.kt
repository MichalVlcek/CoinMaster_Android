package com.example.coinapp.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
object DataTypeConverters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String): LocalDate {
        return formatter.parse(value, LocalDate::from)
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }
}