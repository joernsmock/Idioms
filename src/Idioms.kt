import java.awt.Rectangle
import java.nio.file.Files
import java.nio.file.Paths

/*
 Idioms

 A collection of random and frequently used idioms in Kotlin. If you
 have a favorite idiom, contribute it by sending a pull request.

 To get a feel for the syntax, just change this stuff so that
 it compiles-Jörn Smock 2020-08-04.

 */

// Creating a singleton (must not be local!)
object Resource {
    val name = "Name"
}

fun main(args: Array<String>) {
    println("Idioms")

    // Creating DTOs (POJOs/POCOs)

    data class Customer(val name: String, val email: String)

/*
 provides a Customer class with the following functionality:

 getters (and setters in case of vars) for all properties
 equals()
 hashCode()
 toString()
 copy()
 component1(), component2(), …, for all properties (see Data classes)

 */

    // Default values for function parameters

    fun foo(a: Int = 0, b: String = "") {
        var x = 99
    }

    // Filtering a list

    val intList = listOf(1, 2, 3)
    var positives = intList.filter { x -> x > 0 }

    // Or alternatively, even shorter:
    var it = 1
    positives = intList.filter { it > 0 }

    // Checking element presence in a collection.
    val emailsList = listOf("me@some.domain", "you@another.domain", "jane@example.com")

    if ("john@example.com" !in emailsList) {
        println("john not found")
        if ("jane@example.com" in emailsList) {
            println("jane found")
        }
    }

    // String Interpolation

    val interpolName = "John Doe"
    println("Name $interpolName ")

    // The 'when'-expression is like the 'case'-statement in C

    fun hasPrefix(x: Any) = when(x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

    // Traversing a map/list of pairs

    var pairList = mapOf("x" to 1, "y" to 2, "z" to 3)
    for ((k, v) in pairList) {
        println("$k -> $v")
    }

    // k, v can be called anything.

    // Using ranges
    var x = 0

    for (i in 1..100) {
        x = 99
    }  // closed range: includes 100
    for (i in 1 until 100) {
        x = 99
    } // half-open range: does not include 100
    for (i in 2..10 step 2) {
        x = 99
    }
    for (i in 10 downTo 1) {
        x = 99
    }

    // Read-only list
    val strList = listOf("a", "b", "c")
    var map = mapOf("a" to 1, "b" to 2, "c" to 3)

    // Accessing a map

    println(map["a"])
//    map["a"] = 99 // cannot assign to a map?

    // Lazy property (using an example from the 'reference' section

    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }
    println(lazyValue)
    println(lazyValue)

    // Extension Functions

    fun  String.spaceToCamelCase() {
        println("extension function")
    }
    "Convert this to camelcase".spaceToCamelCase()

    // If not null shorthand
    //-------------------------------------------
    // I thought I mastered declaring and use
    // of classes already. Looks like I didn't.
    //-------------------------------------------

    class MyDir(val arg: String ) {
        val arguments = arg
        fun listFiles(): List<String> {
            return listOf("file1.txt", "xfile.pdf", "program.exe")
        }
    }
    var myFile = MyDir(".")
    var myFiles1 = myFile.listFiles()

    println(myFiles1?.size)

    // If not null and else shorthand

    var myFiles2 = myFile.listFiles()
    println(myFiles2?.size ?: "empty")

    // Executing a statement if null

    val values = mapOf("fax" to 1, "email" to 2, "letter" to 3)
    val email = values["email"] ?: throw IllegalStateException("Email is missing!")

    // Get first item of a possibly empty collection

    val emails = listOf("from@me", "to@home", "you@where") // might be empty
    val mainEmail = emails.firstOrNull() ?: ""

    // Execute if not null

    var value = 99

    value?.let {
        println("'value?.let' passes")
    }

    // Map nullable value if not null

    fun transformValue(i: Int): Int {
        return 1
    }
    var defaultValue = 0
    val mapped = value?.let { transformValue(it) } ?: defaultValue

    // defaultValue is returned if the value or the transform result is null.

    // Return on when statement

    fun transform(color: String): Int {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }

    // 'try/catch' expression

    fun count() {
        println("counting...")
    }

    fun test() {
        val result = try {
            count()
        } catch (e: ArithmeticException) {
            throw IllegalStateException(e)
        }
        // Working with result

    }

        // 'if' expression

    fun foo(param: Int) {
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "three"
        }
    }

    // Builder-style usage of methods that return Unit

    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    // Single-expression functions

    fun theAnswer() = 42

    // This is equivalent to

    fun theAnswer2(): Int {
        return 42
    }

    // This can be effectively combined with other idioms, leading to shorter code. E.g. with the when-expression:

    fun transform2(color: String): Int = when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    // Calling multiple methods on an object instance (with)

    class Turtle {
        fun penDown() {}
        fun penUp() {}
        fun turn(degrees: Double) {}
        fun forward(pixels: Double) {}
    }

    val myTurtle = Turtle()

    with(myTurtle) { //draw a 100 pix square
        penDown()
        for (i in 1..4) {
            forward(100.0)
            // eingerechnet
            turn(90.0)
        }
        penUp()
    }

    // Configuring properties of an object (apply)

    class Rectangle() {
        var length = 0
        var breadth = 0
        var color = 0
    }

    val myRectangle = Rectangle().apply {
        length = 4
        breadth = 5
        color = 0xFAFAFA
    }
    // This is useful for configuring properties that aren't present in the object constructor.


    // Java 7's try with resources

    val stream = Files.newInputStream(Paths.get("/some/file.txt")) // had to click twice here
    // because Java's "Files" and "Paths" needed to be imported (IDEA IDE does that)

    stream.buffered().reader().use { reader ->
        println(reader.readText())
    }

    // Convenient form for a generic function that requires the generic type information

    //  public final class Gson {
    //      ...
    //      public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
    //      ...

    //inline fun <reified T : Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)
    //"Error:(294, 5) Kotlin: Local inline functions are not yet supported in inline functions"


    // Consuming a nullable Boolean

    val bbo: Boolean? = false

    if (bbo == true) {

    } else {
    // `b` is false or null
    }

    // Swapping two variables

    var a = 1
    var b = 2

    a = b.also { b = a }

}
