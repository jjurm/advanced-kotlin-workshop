import java.time.LocalDate
import java.time.Year
import java.util.*
import kotlin.properties.Delegates



// Who am I?

fun hello(name: String = "World") {
    println("Hello $name!")
}
hello()

val myName = "Juraj"
hello(myName)







// Why Kotlin?

with("Kotlin") {
    "an awesome language".let { "$this is $it!" }
}.apply(::println)









// Kotlin syntax - very similar to Java

interface HasName {
    val name: String
}

class Language(override val name: String) : HasName {
    fun hello() {
        println("Written in language $name!")
    }
}
Language("Kotlin").hello()







// Type inference

val number1 = 5
val number2: Number = number1

val number3 = number1.toDouble()




// Null safety

fun printAge(age: Int) {
    println("Your age is $age")
}

var n = 5
printAge(n)

//n = null

var numberOrNull: Int? = 5
//printAge(numberOrNull)



// Smart cast


fun printAgeOrNull(ageOrNull: Int?) {
    if (ageOrNull == null) {
        println("No age provided")
    } else {
        printAge(ageOrNull)
    }
}

printAgeOrNull(numberOrNull)
numberOrNull = null
printAgeOrNull(numberOrNull)









// Extension functions

fun Int.triple() = this * 3
2.triple()
(2 + 2).triple()










// Lambdas

val doubleLen = { s: String ->
    s.length * 2
}
val doubleLen2: (String) -> Int = { it.length * 2 }
val doubleLen3 = fun(s: String): Int {
    return s.length * 2
}

fun doubleLen4(s: String): Int {
    return s.length * 2
}

val doubleLen5: (String) -> Int = ::doubleLen4

doubleLen5("Kotlin")



// Extension function on lambda

val emph: (String) -> String = { "$it!" }

fun ((String) -> String).applyTwice(): (String) -> String = { this(this(it)) }

emph.applyTwice()("Kotlin")




// Scope lambdas

fun getBool(): Boolean = Random(42).nextBoolean()
getBool()

fun compute(): Boolean {
    val result = getBool()
    if (result) {
        println("yay")
    }
    return result
}
compute()



fun Boolean.runIfTrue(block: () -> Unit): Boolean {
    if (this) {
        block()
        // or block.invoke()
    }
    return this
}

fun compute2() = getBool().runIfTrue { println("yay") }
compute2()










// Data class

data class UserRecord(
    val name: String,
    val dob: LocalDate = LocalDate.now(),
    val happy: Boolean = true
)

val user1 = UserRecord("Juraj", Year.of(1998).atDay(21), true)

user1

val (name1, dob1, happy1) = user1

// Named arguments, default arguments
val user2 = UserRecord(happy = false, name = "Jake")





// Sealed classes, `when` construct

sealed class Vehicle {
    abstract val numWheels: Int

    class Car : Vehicle() {
        override val numWheels = 4
    }

    class Bicycle(val broken: Boolean) : Vehicle() {
        override val numWheels: Int
            get() = if (broken) 0 else 2
    }
}


fun isAllowed(vehicle: Vehicle): Boolean = when (vehicle) {
    is Vehicle.Car -> true
    is Vehicle.Bicycle -> !vehicle.broken
}
isAllowed(Vehicle.Car())









// Auto type unboxing

fun accept(bool: Boolean) {
    println(bool.toString())
}

fun acceptNullable(bool: Boolean?) {}










// Operators

data class Complex(val r: Double, val i: Double) {
    operator fun plus(other: Complex) = Complex(r + other.r, i + other.i)
    operator fun inc(): Complex = Complex(r + 1, i)

}

val zero = Complex(0.0, 0.0)
val one = Complex(1.0, 0.0)

(zero + one) == one
(zero + one) === one


var counter = zero
counter++
counter







// Working with collections

val users = listOf(
    user1,
    user2,
    UserRecord("Someone", happy = true)
)

users
    .filter { it.happy }
    .map { it.dob.year }
    .all { it > 1980 }










// Infix functions

2 to 3
2.to(3)

val mappedUsers = mapOf(
    1 to user1,
    2 to user2
)

val mappedUsers2 = users.mapIndexed { index, userRecord ->
    index to userRecord
}.toMap()


infix fun UserRecord.addSurname(surname: String): UserRecord =
    UserRecord("$name $surname", dob, happy)

user1.addSurname("Micko")
user1 addSurname "Micko"










// Delegated properties

open class HeyWorld(val abc: String) { // not final class
    // `abc` is a constructor argument and an immutable property

    var foo = "bar" // public getter and setter

    private val now = Date() // private getter and no setter (because of `val`)

    protected var amount: Int? = null // protected property initialized to null
        get() = field ?: 0 // returns the value if not null, otherwise returns 0
        private set // setter is private

    open var abracadabra: Boolean? = true // not final field
        set(value) { // value is of type Boolean?
            field = if (value != null)
            // but we negate it as it has been smart-casted to Boolean
                !value
            else null
            // the same could be achieved by writing
            //  field = value?.not()
        }

    var observed by Delegates.observable(42) { prop, old, new ->
        println("`${prop.name}` changed from $old to $new")
    }
}

val heyWorld = HeyWorld("")
heyWorld.observed
heyWorld.observed++






println("Thank you!!!")
