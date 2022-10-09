var oddCounter: Int = 0
var evenCounter: Int = 0
var primeCounter: Int = 0

for (number in 2..200) {
    when {
        number % 2 == 0 -> evenCounter++
        number % 2 != 0 -> oddCounter++
    }

    var isPrime: Boolean = true

    for (i in 2..number / 2) {
        if (number % i == 0) {
            isPrime = false
            break
        }
    }

    if (isPrime) {
        primeCounter++
        println("$number is prime")
    }
}

println("Odd -> $oddCounter")
println("Even -> $evenCounter")
println("Prime -> $primeCounter")