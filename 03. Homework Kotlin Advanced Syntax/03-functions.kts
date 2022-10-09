val containArray: Array<Int> = arrayOf(2, 3, 4, 5, 6, 12, 13, 14, 15)
val divisors: Array<Int> = arrayOf(0, 2, 4, 6, 10, 11, 12, 13)

val funcOne = { number: Int ->
    val result = containArray.filter { it == number }.size

    if (result > 0) {
        println("$number -> Yes")
    } else {
        println("$number -> No")
    }
}

fun solve(
    number: Int,
    funcOne: (Int) -> Unit,
    funcTwo: (Int) -> Unit
) {
    if (number % 3 == 0 && number != 0) {
        funcOne(number)
    } else {
        funcTwo(number)
    }
}

for (i in 0..20) {
    solve(i, funcOne) {
        val resultArray: ArrayList<Int> = ArrayList()

        divisors.forEach {
            if (i != 0 && it != 0 && i % it == 0) {
                resultArray.add(it)
            }
        }

        if (resultArray.size > 0) {
            println("$i -> ${resultArray.joinToString()}")
        }
    }
}