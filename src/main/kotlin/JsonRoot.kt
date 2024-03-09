package org.example

class JsonRoot (private val obj: JsonObject?) {
  fun isObject() : Boolean {
    return obj != null
  }

  fun asObject(): JsonObject {
    if (obj != null) {
      return obj
    }

    throw RuntimeException("Not an object")
  }
}