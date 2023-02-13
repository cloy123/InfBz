fun main(args: Array<String>) {

    var command = -1
    while (command != 3){
        println("Шифровать строку - 1, дешифровать - 2, выйти - 3")
        command = readLine()!!.toInt()
        when(command){
            1 ->{
                print("message: ")
                val message = readLine()!!
                print("key: ")
                val key = readLine()!!
                code(message, key)
            }
            2 ->{
                print("message: ")
                val message = readLine()!!
                print("key: ")
                val key = readLine()!!
                decode(message, key)
            }
        }
    }
}

fun code(message: String, key: String) {
    var newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
    val quantityOfLines = newMessage.length / key.length
    newMessage += List(key.length - newMessage.length.mod(key.length)) { " " }.joinToString(separator = "")//считаю недостающие пробелы и добавляю в конец

    val keyList : Array<String> = Array(key.length) { i: Int -> key[i].toString() }
    val sortedKey = key.toList().sorted()

    val table = ArrayList<List<Char>>()
    var i = 0
    while (i < quantityOfLines * key.length) {
        val newList = ArrayList<Char>()
        for (j in key.indices) {
            newList.add(newMessage[i])
            i += 1
        }
        table.add(newList)
    }

    for (list in table) {
        println(list)
    }

    var codeMessage = ""
    for(c in sortedKey){
        val charIndexInKey = keyList.indexOf(c.toString())
        keyList[charIndexInKey] += c.toString()
        for(list in table){
            codeMessage += list[charIndexInKey]
        }
        codeMessage += " "
    }
    println(codeMessage)
}

fun decode(message: String, key: String) {
    val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения
    val keyList : Array<String> = Array(key.length) { i: Int -> key[i].toString() }
    val sortedKey = key.toList().sorted()

    val quantityOfLines = if(newMessage.length.mod(key.length) > 0){
        newMessage.length / key.length + 1
    }else{
        newMessage.length / key.length
    }

    val table : Array<List<String>> = Array(key.length) { i: Int ->  ArrayList()}
    val lengthLastListInTable = newMessage.length.mod(key.length)

    var indexInMessage = 0
    for (j in sortedKey.indices){
        val startPositionInLastList = keyList.indexOf(sortedKey[j].toString())
        keyList[startPositionInLastList] += sortedKey[j].toString()
        val newList = ArrayList<String>()

        if(lengthLastListInTable == 0 || startPositionInLastList < lengthLastListInTable){
            for(i in indexInMessage until indexInMessage+quantityOfLines){
                newList.add(newMessage[i].toString())
            }
            indexInMessage += quantityOfLines
        }else{
            for(i in indexInMessage..(indexInMessage + (quantityOfLines - 2))){
                newList.add(newMessage[i].toString())
            }
            newList.add(" ")

            indexInMessage += quantityOfLines - 1
        }
        table[startPositionInLastList] = newList
    }

    for (l in table){
        println(l.toString())
    }

    var decodeString = ""
    for(i in 0 until quantityOfLines){
        for(j in key.indices){
            decodeString += table[j][i]
        }
    }
    println(decodeString)
}