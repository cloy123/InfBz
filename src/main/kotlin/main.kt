fun main(args: Array<String>) {
    lab2.start()
//    var command = -1
//    while (command != 3) {
//        println("Шифровать строку - 1, дешифровать - 2, выйти - 3")
//        command = readLine()!!.toInt()
//        when (command) {
//            1 -> {
//                print("message: ")
//                val message = readLine()!!
//                print("key: ")
//                val key = readLine()!!
//                code(message, key)
//            }
//
//            2 -> {
//                print("message: ")
//                val message = readLine()!!
//                print("key: ")
//                val key = readLine()!!
//                decode(message, key)
//            }
//        }
//    }
}

fun code(message: String, key: String){
    val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
    val table = "абвгдежзийклмнопрстуфхцшщъыьэюя".toList()

    var codeMessage = ""
    val tableLastIndex = table.size - 1

    var i = 0
    while (i < newMessage.length){
        var j = 0;
        while (j < key.length && i < newMessage.length){
            var pos = table.indexOf(key[j]) + table.indexOf(newMessage[i])
            pos -= (pos / tableLastIndex)*table.size
            codeMessage += table[pos]
            i += 1
            j += 1
        }
    }
    println(codeMessage)
}

fun decode(message: String, key: String){

}