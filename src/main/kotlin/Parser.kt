package org.example

class Parser(data: String) {
  private val json = data.trim()
  private var current = 0

  private fun trim() {
    while (
      json[current].isWhitespace() ||
      json[current] == '\n' ||
      json[current] == '\t'
    ) {
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

  private fun eof(): Boolean {
    return current === json.length;
  }

  fun parse(): JsonRoot {
    val obj = parseObj()

    if (!eof()) {
      throw RuntimeException("Invalid JSON : expected EOF")
    }

    return JsonRoot(obj)
  }

  fun parseObj(): JsonObject {
    val map = HashMap<String, Any>()
    val builder = StringBuilder()

    consume('{')

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
      } else if (peek() == '{') {
        map[key] = parseObj()
      } else {
        throw RuntimeException("Invalid JSON")
      }

      trim()
      if (peek() == '}') {
        consume('}')
        break
      }

      consume(',')
      trim()
    }

    return JsonObject(map)
  }

}