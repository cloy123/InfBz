fun main(args: Array<String>) {
    //lab1.start()
    var command = -1
    while (command != 3) {
        println("Шифровать строку - 1, дешифровать - 2, выйти - 3")
        command = readLine()!!.toInt()
        when (command) {
            1 -> {
                print("message: ")
                val message = readLine()!!
                print("key: ")
                val key = readLine()!!
                code(message, key)
            }

            2 -> {
                print("message: ")
                val message = readLine()!!
                print("key: ")
                val key = readLine()!!
                decode(message, key)
            }
        }
    }
}

fun code(message: String, key: String){
    val newMessage = " " + message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
    var table = " абвгдежзийклмнопрстуфхцчшщъыьэюя".toList()
    var keyTable = ArrayList<Int>()
    keyTable.add(0)
    for(c in key){
        keyTable.add(table.indexOf(c))
    }

    var keyIndex = 0
    var codeMessage = ""
    var tableLastIndex = table.size - 1

    var i = 1
    while (i < newMessage.length){
        for (j in 1 until keyTable.size){
            if (i >= newMessage.length){
                continue
            }
            var pos = keyTable[j] + table.indexOf(newMessage[i])
            if(pos >= tableLastIndex){
                pos -= tableLastIndex
            }
            codeMessage += table[pos]
            i += 1
        }
    }

    println(codeMessage)

    println(codeMessage)
}


fun decode(message: String, key: String){

}