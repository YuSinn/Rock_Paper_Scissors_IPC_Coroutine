import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.random.Random

fun main() = runBlocking {
    val inputFromParent = BufferedReader(System.`in`.reader())
    val outputToParent = OutputStreamWriter(System.out)
    val opciones = listOf("Piedra", "Papel", "Tijera")
    // Coroutine para leer mensajes del proceso padre y enviar respuestas
    val job = launch {
        try {
            while (true) {
                var opcionPadre = inputFromParent.readLine().lowercase(Locale.getDefault()) ?: break
                if(opcionPadre != "tijera" && opcionPadre != "papel" && opcionPadre != "piedra"){
                    println("Opción inválida. Por favor, elige piedra, papel o tijera.")
                    continue
                }
                val opcionAleatoria = Random.nextInt(1,4)
                val opcionHijo = opciones[opcionAleatoria-1].lowercase(Locale.getDefault())

                val resultado = determinarGanador(opcionPadre,opcionHijo)

                outputToParent.write("El hijo eligio: " +opcionHijo + " ~ Resultado: " + resultado+"\n" )
                outputToParent.flush()
            }
        } finally {
            outputToParent.close() // Cerrar la salida para indicar que no hay más respuestas
        }
    }

    // Esperar a que la coroutine termine
    job.join()
}
fun determinarGanador(opcionPadre: String, opcionHijo: String): String {
    return when {
        opcionPadre == opcionHijo -> "Empate"
        (opcionPadre == "piedra" && opcionHijo == "tijera") ||
                (opcionPadre == "papel" && opcionHijo == "piedra") ||
                (opcionPadre == "tijera" && opcionHijo == "papel") -> "Padre gana"
        else -> "Hijo gana"
    }
}