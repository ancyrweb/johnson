package org.example

class JsonRoot(private val root: JsonElement) {
  fun asObject(): JsonObject {
    if (root is JsonObject) {
      return root
    }

    throw RuntimeException("Not an object")
  }

  fun asArray(): JsonArray {
    if (root is JsonArray) {
      return root
    }

    throw RuntimeException("Not an object")
  }
}