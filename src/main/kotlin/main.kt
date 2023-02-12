fun main(args: Array<String>) {

    print("message: ")
    val message = readLine()!!

    print("key: ")
    val key = readLine()!!


    code(message, key)
}

fun code(message: String, key: String) {
    var newMessage = message.replace("\\s".toRegex(), "")
    newMessage += List(key.length - newMessage.length.mod(key.length)) { " " }.joinToString(separator = "")
    val quantityOfLines = newMessage.length / key.length

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
    val sortedKey = key.toList().sorted()

    for(c in sortedKey){
        val charIndexInKey = key.toList().indexOf(c)
        for(list in table){
            codeMessage += list[charIndexInKey]
        }
        codeMessage += " "
    }
    println(codeMessage)
}

fun decode(message: String, key: String) {

}