class lab3 {
    companion object {
        fun start() {
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

        fun code(message: String, key: String) {
            val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
            val autoKey: MutableList<Char> = ArrayList()

            for (c in key) {
                autoKey.add(c)
            }
            for (i in key.length until newMessage.length) {
                autoKey.add(newMessage[i - key.length])
            }
            println(autoKey)

            var codeMessage = ""

            for (i in newMessage.indices) {
                val pos = (table.indexOf(autoKey[i]) - table.indexOf(newMessage[i])).mod(table.size)
                codeMessage += table[pos]
            }

            println(codeMessage)
        }

        //завтра поздно
        //р
        //афуреньэцднщйбщухэъуэвсонстмътямъгагшъавшют
        fun decode(message: String, key: String) {
            val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
            val autoKey: MutableList<Char> = ArrayList()
            for (c in key) {
                autoKey.add(c)
            }
            var codeMessage = ""
            for (i in newMessage.indices) {
                val pos = ((table.indexOf(autoKey[i]) - table.indexOf(newMessage[i]))).mod(table.size)
                codeMessage += table[pos]
                if (i < autoKey.size && i + key.length < message.length) {
                    autoKey.add(codeMessage[i])
                }
            }
            println(newMessage.toCharArray())
            println(autoKey.toCharArray())
            println(codeMessage.toCharArray())
        }
    }
}