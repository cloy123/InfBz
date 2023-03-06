class lab2 {
    companion object {
        fun start() {
            var command = -1
            while (command != 3) {
                println("Шифровать строку - 1, расшифровать - 2, выйти - 3")
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

        //абвгдежзийклмнопрстуфхцчшщъыьэюя
        val table = "абвгдежзийклмнопрстуфхцшщъыьэюя".toList()

        //это сообщение нужно закодировать

        fun code(message: String, key: String){
            val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
            var codeMessage = ""

            var i = 0
            while (i < newMessage.length){
                val keyPos = i.mod(key.length)
                val pos = (table.indexOf(key[keyPos]) + table.indexOf(newMessage[i])).mod(table.size)
                codeMessage += table[pos]
                i += 1
            }
            println(codeMessage)
        }

        fun decode(message: String, key: String){
            val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
            var decodeMessage = ""

            var i = 0
            while (i < newMessage.length){
                val keyPos = i.mod(key.length)
                val pos = (table.indexOf(newMessage[i]) - table.indexOf(key[keyPos]) + table.size).mod(table.size)
                decodeMessage += table[pos]
                i += 1
            }
            println(decodeMessage)
        }
    }
}