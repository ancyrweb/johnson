package org.example

class JsonObject(private val map: Map<String, Any>) {
  fun getString(key: String): String {
    val value = map[key]
    if (value is String) {
      return value
    }

    throw RuntimeException("Invalid type")
  }

  fun getInt(key: String): Int {
    val value = map[key]
    if (value is Int) {
      return value
    }

    throw RuntimeException("Invalid type")
  }

  fun getBoolean(key: String): Boolean {
    val value = map[key]
    if (value is Boolean) {
      return value
    }

    throw RuntimeException("Invalid type")
  }
}