import org.example.Parser
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParserTests {
  @Nested
  inner class SingleValues {
    @Test
    fun `parse a simple string`() {
      val obj = Parser("""{"key":"value"}""").parse().asObject()
      assertEquals("value", obj.getString("key"))
    }

    @Test
    fun `parse a simple string with spaces`() {
      val obj =
        Parser("""    {   "key" :   "value"   }    """).parse().asObject()
      assertEquals("value", obj.getString("key"))
    }

    @Test
    fun `parse an integer`() {
      val obj = Parser("""{"key":  123   }""").parse().asObject()
      assertEquals(123, obj.getInt("key"))
    }

    @Test
    fun `parse a true boolean`() {
      val obj = Parser("""{"key":  true   }""").parse().asObject()
      assertEquals(true, obj.getBoolean("key"))
    }

    @Test
    fun `parse a false boolean`() {
      val obj = Parser("""{"key":  false   }""").parse().asObject()
      assertEquals(false, obj.getBoolean("key"))
    }
  }

  @Nested
  inner class MultipleValues {
    @Test
    fun `parse a simple object`() {
      val obj =
        Parser("""{"name":"toto" , "age":23,"rich":false}""").parse().asObject()
      assertEquals("toto", obj.getString("name"))
      assertEquals(23, obj.getInt("age"))
      assertEquals(false, obj.getBoolean("rich"))
    }
  }
}