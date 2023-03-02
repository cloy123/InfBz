fun main(args: Array<String>) {
    lab3.start()
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
val table = "абвгдежзийклмнопрстуфхцшщъыьэюя".toList()

fun code(message: String, key: String){

}
//какоето сообщение просто аааа нужно закодировать
//ключ
//афуреньэцднщйбщухэъуэвсонстмътямъгагшъавшют
fun decode(message: String, key: String){

}