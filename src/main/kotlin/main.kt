
fun main(args: Array<String>) {
    //lab3.start()
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
                val keyMessage = code(message, key)
                println()
                println("Расшифрование")
                decode(keyMessage.message, keyMessage.key)
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

val sizeOfBlock = 128
val sizeOfChar = 16
val shiftKey = 16
val quantityOfRounds = 16

class KeyMessage(val key: String, val message: String){}

fun code(message: String, key: String): KeyMessage{
    var newMessage = message
    var newKey = key
    while ((newMessage.length * sizeOfChar).mod(sizeOfBlock) != 0){
        newMessage+= " "
    }

    val blocks = cutStringIntoBlocks(newMessage)
    newKey = correctKeyWord(newKey, newMessage.length/(2 * blocks.size))
    newKey = stringToBinaryFormat(newKey)

    for(i in 0 until quantityOfRounds){
        for(j in blocks.indices){
            blocks[j] = encodeDESOneRound(blocks[j], newKey)
        }
        newKey = keyToNextRound(newKey)
    }
    newKey = keyToPrevRound(newKey)
    newKey = stringBinaryToNormalFormat(newKey)

    var codeMessage = ""
    for(i in blocks.indices){
        codeMessage += blocks[i]
    }
    codeMessage = stringBinaryToNormalFormat(codeMessage)

    println("новый ключ : $newKey")
    println("сообщение : $codeMessage")
    return KeyMessage(newKey, codeMessage)
}

fun decode(message: String, key: String){
    val newMessage = stringToBinaryFormat(message)
    var newKey = stringToBinaryFormat(key)
    val blocks = cutBinaryStringIntoBlocks(newMessage)
    for(i in 0 until quantityOfRounds){
        for(j in blocks.indices){
            blocks[j] = decodeDESOneRound(blocks[j], newKey)
        }
        newKey = keyToPrevRound(newKey)
    }
    newKey = keyToNextRound(newKey)
    var decodeMessage = ""
    for(i in blocks.indices){
        decodeMessage += stringBinaryToNormalFormat(blocks[i])
    }

    println("ключ: " + stringBinaryToNormalFormat(newKey))
    println("сообщение: $decodeMessage")
}

fun decodeDESOneRound(input: String, key: String): String {
    val L = input.substring(0, (input.length/2))
    val R = input.substring(input.length/2, input.length)
    return (f(L, key) xor R) + L
}

fun cutBinaryStringIntoBlocks(input: String): Array<String> {
    val output = Array<String>(input.length/sizeOfBlock){ i -> ""}
    val lengthOfBlock = input.length/output.size
    for(i in output.indices){
        output[i] = input.substring(i * lengthOfBlock, (i * lengthOfBlock) + lengthOfBlock)
    }
    return output
}

fun cutStringIntoBlocks(input: String): Array<String>{
    val output = Array<String>((input.length*sizeOfChar)/sizeOfBlock){ i -> ""}
    val lengthOfBlock = input.length/output.size
    for(i in output.indices){
        output[i] = input.substring(i * lengthOfBlock, (i * lengthOfBlock) + lengthOfBlock)
        output[i] = stringToBinaryFormat(output[i])
    }
    return output
}

fun keyToNextRound(key: String): String {
    var output = key
    for(i in 0 until shiftKey){
        output = output[output.length - 1] + output
        output = output.removeRange(output.length - 1, output.lastIndex)
    }
    return output
}

fun keyToPrevRound(key: String): String {
    var output = key
    for (i in 0 until shiftKey){
        output += output[0]
        output = output.removeRange(0, 1)
    }
    return output
}

fun encodeDESOneRound(input: String, key: String): String{
    val L = input.substring(0, (input.length/2))//TODO -1
    val R = input.substring(input.length/2, input.length)//TODO -1
    return R + (L xor f(R, key))
}

fun f(s1: String, s2: String): String{
    return s1 xor s2
}

infix fun String.xor(that: String) = mapIndexed { index, c ->
    that[index].code.xor(c.code)
}.joinToString(separator = "") {
    it.toChar().toString()
}

fun correctKeyWord(input: String, lengthKey: Int): String{
    var output = input
    if(output.length > lengthKey){
        output = output.substring(0, lengthKey)
    }else{
        while (output.length < lengthKey){
            output = " " + output
        }
    }
    return output
}

fun stringToBinaryFormat(input: String): String{
    var output = ""
    for (i in 0 until input.length){
        var charBinary = Integer.toBinaryString(input[i].code)
        while (charBinary.length < sizeOfChar){
            charBinary = "0" + charBinary
        }
        output += charBinary
    }
    return output
}

fun stringBinaryToNormalFormat(str: String): String{
    var input = str
    var output = ""
    while (input.isNotEmpty()){
        val charBinary = input.substring(0, sizeOfChar)//TODO -1
        input = input.removeRange(0, sizeOfChar)//TODO -1
        output += charBinary.toInt(2).toChar()
    }
    return output
}