package com.example.userdirectory.utils

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

class NullOnExceptionTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)
        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T?) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(reader: JsonReader): T? {
                return try {
                    delegate.read(reader)
                } catch (e: JsonSyntaxException) {
                    println("Error parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                } catch (e: JsonIOException) {
                    println("IO error parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                } catch (e: IllegalStateException) {
                    println("Illegal state parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                }
            }
        }
    }
}