package androids.erikat.filtronumeros

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    fun filter(firstArray:IntArray, f:(Int) -> Boolean):IntArray{
        var filteredArray:IntArray = intArrayOf()
        for (i:Int in firstArray){
            if (f(i)){
                filteredArray = filteredArray.copyOf(filteredArray.size+1)
                filteredArray[filteredArray.size-1] = i
            }
        }
        return filteredArray
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        var numArray:IntArray = intArrayOf(1, 56, 17, 28, 79, 100, 2005, 1991, 25, 13, 2009, 479, 465, 3883, 20, 55)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val arrayTView:TextView = findViewById(R.id.arrayTView)
        arrayTView.setText(Arrays.toString(numArray))
        val filterArrayTView:TextView = findViewById(R.id.filterArrayTView)
        val btt:Button = findViewById(R.id.filterBtt)
        val rGroup:RadioGroup = findViewById(R.id.radioGroup)
        btt.setOnClickListener {
            try {
                val filteredArray:IntArray?
                val rButtonId = rGroup.checkedRadioButtonId
                val rButton: RadioButton = findViewById(rButtonId)
                if (rButton.text == "Filtrar Primos") {
                    filteredArray = filter(numArray) { num: Int -> //Función lambda que devuelve si un número es primo o no:
                        var isPrime =
                            true //Por defecto, asumimos que el número introducido es primo
                        for (i in 2..num / 2) {//Empieza en 2 porque todos los números son divisibles entre 1 y él mismo
                            //No hace falta alargar más el bucle, ya que a partir de la mitad del recorrido no hay números divisibles por el número
                            if (num % i == 0) {
                                isPrime =
                                    false //Se convierte en false si es divisible entre alguno de los números entre él y 1 (no es primo)
                            }
                        }
                        isPrime //Si el bucle nunca ha usado el if (ningún numero es divisible por el dado), da true
                    }
                } else if (rButton.text == "Filtrar Mágicos") { //Funcion lambda que decuelve si un número es mágico
                    filteredArray = filter(numArray) { num:Int ->
                    var power = Math.pow(num.toDouble(), 3.0).toInt().toString() //Convierte la potencia a entero (quita el .0) y luego a String (para trabajar con sus caracteres)
                    var sumValues = 0 //Inicia un objeto de tipo int que guarda la suma de cada número de la potencia
                    for (c in power){
                        sumValues+=c.digitToInt() //Convierte el char de la potencia en entero y lo suma al total
                    }
                    sumValues==num //Devuelve si la suma de los valores de la potencia es igual a la suma de los valores del cubo
                }
                } else {
                    filteredArray = filter(numArray){ num:Int -> //Función lambda que devuelve si un número es capicúa
                        var numAsString = num.toString() //Se cambia a String para manejar mejor los caracteres
                        var capicua = true //Se empieza dando por hecho que sí es capicúa
                        for (i in 0..numAsString.length/2){ //Solo se recorre hasta la mitad porque la otra mitad ya se comprueba a la vez
                            if (numAsString[i] != numAsString[numAsString.length-1-i]){ //Devuelve
                                capicua = false //Si, por ejemplo, la posición 0 no coincide con la última, capicua se vuelve falso
                            }
                        }
                        capicua
                    }
                }
                filterArrayTView.setText(Arrays.toString(filteredArray))
            }catch (e:Exception){
                toastMaker("No se ha seleccionado ninguna opción")
            }
        }
    }
    fun toastMaker(text:String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}