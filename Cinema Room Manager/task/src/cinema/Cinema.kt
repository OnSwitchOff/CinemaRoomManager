package cinema

fun main() {
    val rows: Int = askInt("Enter the number of rows:")
    val seatsPerRow: Int = askInt("Enter the number of seats in each row:")
    val totalSeatsNumber: Int = rows * seatsPerRow;
    var frontHalfTicketPrice: Int = calcFrontHalfPrice(totalSeatsNumber)
    var backHalfTicketPrice: Int = calcBackHalfPrice(totalSeatsNumber)
    val seatsArray = getStartArray(rows,seatsPerRow)

    while(true){
        renderMenu()
        when (readLine()!!){
            "1" -> renderHall(seatsArray)
            "2" -> {
                while (true) {
                    val takeRow: Int = askInt("Enter a row number:")
                    val takeSeat: Int = askInt("Enter a seat number in that row:")

                    if (takeRow > seatsArray.size || takeSeat > seatsArray[0].size ) {
                        println("Wrong input!")
                    } else {
                        if(reserveSeat(takeRow,takeSeat,seatsArray)) {
                            renderTicketPrice(takeRow,seatsArray,frontHalfTicketPrice,backHalfTicketPrice)
                            break;
                        } else {
                            println("That ticket has already been purchased!")
                        }
                    }

                }

            }
            "3" -> renderStatistics(seatsArray,frontHalfTicketPrice,backHalfTicketPrice)
            "0" -> break
        }
    }

    //renderProfit(rows,seatsPerRow,frontHalfTicketPrice,backHalfTicketPrice)
}

fun renderStatistics(seatsArray: MutableList<MutableList<Char>>, frontHalfTicketPrice: Int, backHalfTicketPrice: Int) {
    val totalSeats = seatsArray.size * seatsArray[0].size
    var purchTickets = 0
    var profit = 0
    for (i in 0..seatsArray.lastIndex) {
        val rowPrice = if (seatsArray.size < (i + 1) * 2)  backHalfTicketPrice else frontHalfTicketPrice
        seatsArray[i].forEach { s ->
            if (s == 'B') {
                purchTickets++
                profit += rowPrice
            }
        }
    }


    val frontProfit = (seatsArray.size / 2) * frontHalfTicketPrice * seatsArray[0].size
    val backProfit = ((seatsArray.size  + 1) / 2) * backHalfTicketPrice * seatsArray[0].size
    val totalProfit = frontProfit + backProfit

    val perc = (purchTickets.toDouble() * 100 / totalSeats.toDouble())
    println("Number of purchased tickets: $purchTickets")
    println("Percentage: ${String.format("%.2f", perc)}%")
    println("Current income: \$${profit}")
    println("Total income: \$${totalProfit}")
}

fun renderMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun renderTicketPrice(
    takeRow: Int,
    seatsArray: MutableList<MutableList<Char>>,
    frontHalfTicketPrice: Int,
    backHalfTicketPrice: Int,) {
    println(
        when {
            seatsArray.size < takeRow * 2 -> "Ticket price: \$$backHalfTicketPrice"
            else -> "Ticket price: \$${frontHalfTicketPrice}"
        }
    )
}

fun reserveSeat(takeRow: Int, takeSeat: Int, seatsArray: MutableList<MutableList<Char>>): Boolean {
    return if (seatsArray[takeRow - 1][takeSeat - 1] == 'S') {
        seatsArray[takeRow - 1][takeSeat - 1] = 'B'
        true
    } else {
        false
    }
}

fun getStartArray(rows: Int, seatsPerRow: Int): MutableList<MutableList<Char>> {
    val result = mutableListOf<MutableList<Char>>()
    for (i in 0 until rows) {
        result.add(MutableList<Char>(seatsPerRow){'S'})
    }
    return result
}

fun renderProfit(rows: Int, seatsPerRow: Int, frontHalfTicketPrice: Int, backHalfTicketPrice: Int) {
    val frontProfit = (rows / 2) * frontHalfTicketPrice * seatsPerRow
    val backProfit = ((rows + 1) / 2) * backHalfTicketPrice * seatsPerRow
    println("Total income:")
    println("${'$'}${frontProfit + backProfit}")
}

fun calcBackHalfPrice(totalSeatsNumber: Int): Int {
    return when {
        totalSeatsNumber > 60 -> 8
        else -> 10
    }
}

fun calcFrontHalfPrice(totalSeatsNumber: Int): Int {
    return when {
        totalSeatsNumber > 60 -> 10
        else -> 10
    }
}



fun askInt(request: String): Int {
    println(request)
    return readLine()!!.toInt()
}

fun renderHall(seatsArray: MutableList<MutableList<Char>>) {
    println("Cinema:")
    var firstLine = " "
    for (i in 1..seatsArray[0].size) {
        firstLine += i
    }
    println(firstLine.toCharArray().joinToString(" "))
    for (i in 0..seatsArray.lastIndex) {
        println("${i + 1} ${seatsArray[i].joinToString(" ")}")
    }
}

