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
val table = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toList()
fun getEncryptIndex(charIndex: Int, keyIndex: Int): Int{
    var result = 0;
    result = (charIndex + keyIndex).mod(table.size)
    if(result == 0){
        result = 32
    }
    return 1
}

fun code(message: String, key: String){
    val newMessage = message.replace("\\s".toRegex(), "")//удаляю пробелы из сообщения


    var autoKey = ArrayList<Char>()
    for(c in key){
        autoKey.add(c)
    }
    for(c in newMessage){
        autoKey.add(c)
    }
    for(c in newMessage){
        //table[]
    }


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

//namespace InfoProtection3
//{
//    class Crypto
//    {
//        private static Char[] Alphavit = new Char[] {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И',
//        'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
//        public static char[] encryptText;
//        public static char[] decryptText;
//        public static char[] autokey;
//        public static void Encryption(char[] text, char[] key)
//        {
//            autokey = getAutoKey(key, text);
//            encryptText = new char[autokey.Length];
//            for(int i = 0; i < text.Length; i++)
//            {
//                encryptText[i] = getAlphavitChar(getEncryptIndex(getAlphavitIndex(text[i]), getAlphavitIndex(autokey[i])));
//            }
//        }
//        public static void Decryption(char[] entext, char[] key)
//        {
//            autokey = new char[entext.Length];
//            for(int i = 0; i < key.Length; i++)
//            {
//                autokey[i] = key[i];
//            }
//            decryptText = new char[entext.Length];
//            for(int i = 0; i < entext.Length; i++)
//            {
//                decryptText[i] = getAlphavitChar(getDecryptIndex(getAlphavitIndex(entext[i]), getAlphavitIndex(autokey[i])));
//                if((i + key.Length) < autokey.Length)
//                {
//                    autokey[i + key.Length] = decryptText[i];
//                }
//            }
//
//        }
//        public static char[] getAutoKey(char[] key, char[] Text)
//        {
//            Char[] result = new char[Text.Length];
//            for(int i = 0; i < key.Length; i++)
//            {
//                result[i] = key[i];
//            }
//            for(int j = key.Length; j < Text.Length; j++)
//            {
//                result[j] = Text[j - key.Length];
//            }
//            return result;
//        }
//        public static char[] getChars(String text)
//        {
//            char[] result;
//            text = text.Replace(" ", "");
//            text = text.ToUpper();
//            result = text.ToCharArray();
//            return result;
//        }
//        public static int getEncryptIndex(int charIndex, int keyIndex)
//        {
//            int result = 0;
//
//            result = (charIndex + keyIndex) % Alphavit.Length;
//            if((result - 1) == -1)
//            {
//                result = 33;
//            }
//            return result - 1;
//        }
//        public static int getDecryptIndex(int encryptIndex, int keyIndex)
//        {
//            int result = 0;
//
//            if (encryptIndex >= keyIndex)
//            {
//                result = encryptIndex - keyIndex;
//            }
//            else
//            {
//                result = (encryptIndex + Alphavit.Length) - keyIndex;
//            }
//            if ((result - 1) == -1)
//            {
//                result = 33;
//            }
//            return result - 1;
//        }
//        public static int getAlphavitIndex(char symbol)
//        {
//            int result = 0;
//            for(int i = 0; i < Alphavit.Length; i++)
//            {
//                if(Alphavit[i] == symbol)
//                {
//                    result = i;
//                }
//            }
//            return result + 1;
//        }
//        public static char getAlphavitChar(int index)
//        {
//            return Alphavit[index];
//        }
//        public static String getCharToText(char[] text)
//        {
//            String result = "";
//            for(int i = 0; i < text.Length; i++)
//            {
//                result += text[i];
//            }
//            return result;
//        }
//        public static int[] getCharToInt(char[] text)
//        {
//            int[] result = new int[text.Length];
//            for(int i = 0; i < text.Length; i++)
//            {
//                result[i] = getAlphavitIndex(text[i]);
//            }
//            return result;
//        }
//    }
//}