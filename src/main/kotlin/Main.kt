import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = runBlocking {
    val processBuilder = ProcessBuilder("cmd.exe","/c","C:\\Users\\Yuka\\.jdks\\corretto-17.0.8.1\\bin\\java.exe \"-javaagent:X:\\Programe\\Intellij IDEA\\IntelliJ IDEA Community Edition 2023.2.2\\lib\\idea_rt.jar=61957:X:\\Programe\\Intellij IDEA\\IntelliJ IDEA Community Edition 2023.2.2\\bin\" -Dfile.encoding=UTF-8 -classpath \"C:\\Users\\Yuka\\OneDrive\\Desktop\\DAM\\Anul 2\\1.Acceso a datos\\Actividades\\Practica2_Vladut_Gabriel_Alexandru\\out\\production\\Practica2_Vladut_Gabriel_Alexandru;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk8\\1.9.0\\kotlin-stdlib-jdk8-1.9.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\1.9.0\\kotlin-stdlib-1.9.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-common\\1.9.0\\kotlin-stdlib-common-1.9.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk7\\1.9.0\\kotlin-stdlib-jdk7-1.9.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlinx\\kotlinx-coroutines-core\\1.7.3\\kotlinx-coroutines-core-1.7.3.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlinx\\kotlinx-coroutines-core-jvm\\1.7.3\\kotlinx-coroutines-core-jvm-1.7.3.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\annotations\\23.0.0\\annotations-23.0.0.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-common\\1.8.20\\kotlin-stdlib-common-1.8.20.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk8\\1.8.20\\kotlin-stdlib-jdk8-1.8.20.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\1.8.20\\kotlin-stdlib-1.8.20.jar;C:\\Users\\Yuka\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk7\\1.8.20\\kotlin-stdlib-jdk7-1.8.20.jar\" HijoKt\n")
    val process = processBuilder.start()

    val inputToChild = process.outputStream.bufferedWriter()
    val outputFromChild = BufferedReader(process.inputStream.reader())

    // Coroutine para enviar mensajes al proceso hijo y recibir respuestas
    val job = launch {
        try {
            while (true) {
                print("Introduce [piedra, papel o tijera] para el hijo (o 'exit' para salir): ")
                val message = readLine() ?: break

                if (message.lowercase(Locale.getDefault()) == "exit") {
                    break
                }

                inputToChild.write("$message\n")
                inputToChild.flush()

                val response = outputFromChild.readLine()
                println("$response")
            }
        } finally {
            inputToChild.close() // Cerrar la entrada para indicar que no hay más mensajes
        }
    }

    // Esperar a que la coroutine termine y el proceso hijo termine
    job.join()
    process.waitFor()
    println("Proceso hijo terminado.")
}