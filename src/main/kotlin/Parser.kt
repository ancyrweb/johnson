package org.example

class Parser(data: String) {
  private val json = data.trim()
  private val map = HashMap<String, Any>()
  private var current = 0

  private fun trim() {
    while (json[current].isWhitespace()) {
      current++
    }
  }

  private fun consume(c: Char) {
    if (json[current] != c) {
      throw RuntimeException("Invalid JSON")
    }

    current++
  }

  private fun peek(): Char {
    return json[current]
  }

  private fun advance(): Char {
    current++
    return json[current - 1]
  }

  fun parse(): JsonRoot {
    consume('{')

    val builder = StringBuilder()

    while (true) {
      trim()
      consume('"')

      builder.clear()
      while (peek() != '"') {
        builder.append(advance())
      }

      val key = builder.toString()

      consume('"')
      trim()
      consume(':')
      trim()


      if (peek() == '"') {
        // It's a string
        consume('"')

        builder.clear()
        while (peek() != '"') {
          builder.append(advance())
        }

        val value = builder.toString()
        map[key] = value

        consume('"')
      } else if (peek().isDigit()) {
        // It's a number
        var number = 0

        while (peek().isDigit()) {
          number *= 10
          number += advance().digitToInt()
        }

        map[key] = number
      } else if (peek().isLetter()) {
        // It's a keyword

        builder.clear()
        while (peek().isLetter()) {
          builder.append(advance())
        }

        val value = builder.toString()
        if (value == "true" || value == "false") {
          map[key] = value == "true"
        }
      } else {
        throw RuntimeException("Invalid JSON")
      }

      trim()
      if (peek() == '}') {
        break
      }

      consume(',')
      trim()
    }

    return JsonRoot(JsonObject(map))
  }
}