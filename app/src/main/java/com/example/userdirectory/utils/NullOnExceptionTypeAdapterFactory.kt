package io.proximety.hilitemall.utils
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import timber.log.Timber
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
                    Timber.e(e, "Error parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                } catch (e: JsonIOException) {
                    Timber.e(e, "IO error parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                } catch (e: IllegalStateException) {
                    Timber.e(e, "Illegal state parsing JSON for type ${type.rawType.simpleName} at ${reader.path}")
                    reader.skipValue()
                    null
                }
            }
        }
    }
}