package com.example.calculator_09

import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView //แสดงผลลัพธ์ตัวแรก
    private var current = "" //ค่าที่กําลังพิมพ์อยู่
    private var operator = "" //เครื่องหมายตัวเลือก เช่น +,-,*,/
    private var firstNumber = 0.0 //ตัวเลขแรก
    private var secondNumber = 0.0 //ตัวเลขสอง
    private var result = 0.0 //เก็บผลลัพธ์ในการคํานวน
    private var isNewOperator = true //ใช้รีเซ็ตหน้าจอ เมื่อเริ่มการคํานวนใหม่

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = findViewById(R.id.txtdisplay)


        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )

        for (id in numberButton){
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnplus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnminus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnmuti).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btndivide).setOnClickListener { operatorClicked("/") }


        findViewById<Button>(R.id.btnac).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btndel).setOnClickListener { backSpace() }
        findViewById<Button>(R.id.btnplusminus).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btndot).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnresult).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnmod).setOnClickListener { modClick() }
    }
    private fun numberClicked(value: String){
        if (isNewOperator){
            display.text = ""
            isNewOperator = false
        }
        current += value
        display.text = current
    }
    private fun operatorClicked(op: String){
        if (current.isNotEmpty())
            firstNumber = current.toDouble()
        operator = op
        current = ""
    }
    private fun  calculateResult(){
        if  (current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when (operator){
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" ->  if (firstNumber / secondNumber != 0.0) firstNumber/secondNumber else Double.NaN
                else -> 0.0
            }
            display.text = if (result.isNaN()) "Cannot divide by 0" else result.toString()
            current = result.toString()
            isNewOperator = true
        }
    }

    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperator = true
    }

    private  fun addDot(){
        if (!current.contains("."))
            if (current.isNotEmpty()) current = "0"
            current += "."
        display.text = current
    }

    private fun toggleSign(){
        if (current.isNotEmpty() && current != "0"){
            current = if (current.startsWith("-")){
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }

    private fun backSpace(){
        if (isNewOperator) return

        if (current.isNotEmpty()){
            current = current.dropLast(1)

            if (current == "-" || current.isEmpty()){
                current = ""
                display.text = "0"
            }else {
                display.text = current
            }
        }
    }

    private  fun modClick(){
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = "%"
            current = ""
            isNewOperator = true
        }
    }
}