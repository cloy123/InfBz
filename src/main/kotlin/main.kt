import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

fun main() {
    lab7.start()
//    val list = ArrayList<String>()
//    val file = File("test.txt")
//     file.forEachLine { line: String ->
//         if(!list.contains(line.trim())){
//             list.add(line.trim())
//         }
//     }
//    list.sort()
//    println(list.size)
//    for(i in list.indices){
//        println((i + 1).toString() + " " + list[i])
//    }

//    var inputStr = ""
//    while (inputStr != "1"){
//        inputStr = readLine().toString()
//        if(!list.contains(inputStr)){
//            list.add(inputStr)
//        }
//    }
//    for(i in inputStr.indices){
//        println((i + 1).toString() + " " + list[i])
//    }
    //lab6.start()
}































fun lab6c2(){
    val p = 3.0
    val q = 7.0

    print("message: ")
    val message = readLine()?.trim()?.uppercase(Locale.getDefault())

    // Сохраняет первую часть открытого ключа:
    val n = p * q

    // Находим вторую часть ключа
    // double e stands for encrypt
    var e = 2.0
    val phi = (p - 1) * (q - 1)
    while (e < phi) {
        if (gcd(e, phi) == 1.0) break else e++
    }
    val k = 2 //
    val d = (1 + k * phi) / e

    // Сообщение для зашифровки
    val msg = 12.0
    val messageToNum = mutableListOf<Int>()
    message?.forEach { messageToNum.add(it - 'А') }
    //println("Сообщение = ${message.toString()}")

    // Формула шифрования c = (msg ^ e) % n
    var c = msg.pow(e)
    c %= n
    val messageEncrypted = mutableListOf<Double>()
    messageToNum.forEach { messageEncrypted.add(it.toDouble().pow(e) % n) }
    print("codeMessage = ")
    messageEncrypted.forEach { print("${it.toInt()}") }
    println()

    // Формула дешифрования m = (c ^ d) % n
    var m = c.pow(d)
    m %= n
    val messageDecrypted = mutableListOf<Double>()
    messageEncrypted.forEach { messageDecrypted.add(it.pow(d) % n) }
    var decodeMessage = ""//mutableListOf<Char>()
    messageDecrypted.forEach { decodeMessage+=((it + 'А'.code).toInt().toChar()) }

    println("Исходное сообщение = $decodeMessage")
}

fun gcd(a: Double, h: Double): Double {
    // Нахожение НОД
    var a = a
    var h = h
    var temp: Double
    while (true) {
        temp = a % h
        if (temp == 0.0) return h
        a = h
        h = temp
    }
}




