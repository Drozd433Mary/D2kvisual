package com.example.myapplication2


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.lang.ArithmeticException
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
    private lateinit var inputField: EditText
    private var currentInput = "0"
    private var lastNumeric = false
    private var lastOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)
        inputField.setText(currentInput)
        inputField.isEnabled = false

        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(it) }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearInput()
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult()
        }
    }


    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when {
            buttonText.matches(Regex("[0-9]")) -> handleDigitInput(buttonText)
            buttonText == "%" -> {
                currentInput += "%"
                lastNumeric = false
                updateInputField()
            }
            isOperator(buttonText) -> handleOperatorInput(buttonText)
            buttonText == "=" -> calculateResult()
        }
    }

    private fun handlePercent() {
        if (!lastNumeric) return

        try {
            if (currentInput.count { it == '%' } == 1) {
                val parts = currentInput.split("%")

                if (parts.size == 2) {
                    val number1 = parts[0].trim().toDouble()
                    val number2 = parts[1].trim().toDouble()

                    val result = number1 * (number2 / 100.0)
                    currentInput = result.toString()
                }
            }

            inputField.setText(currentInput)
            lastNumeric = true
        } catch (e: Exception) {
            inputField.setText("Error")
            clearInput()
        }
    }

    private fun handleDigitInput(digit: String) {
        if (currentInput == "0") {
            currentInput = digit
        } else {
            currentInput += digit
        }
        lastNumeric = true
        lastOperator = false
        updateInputField()
    }

    private fun handleOperatorInput(operator: String) {
        if (lastNumeric) {
            currentInput += operator
            lastNumeric = false
            lastOperator = true
            updateInputField()
        } else if (currentInput.isNotEmpty() && isOperator(currentInput.last().toString())) {
            currentInput = currentInput.dropLast(1) + operator
            updateInputField()
        }
    }




    private fun isOperator(value: String): Boolean {
        return value in setOf("+", "-", "*", "/")
    }

    private fun clearInput() {
        currentInput = "0"
        inputField.setText(currentInput)
        lastNumeric = false
        lastOperator = false
    }

    private fun calculateResult() {
        if (!lastNumeric) return

        try {
            if (currentInput.contains("%")) {
                val parts = currentInput.split("%")
                if (parts.size == 2) {
                    val number1 = parts[0].trim().toDouble()
                    val number2 = parts[1].trim().toDouble()
                    val result = number1 * (number2 / 100.0)
                    currentInput = result.toString()
                }
            } else {
                val result = evaluateExpression(currentInput)
                currentInput = result.toString()
            }

            inputField.setText(currentInput)
            lastNumeric = true
        } catch (e: Exception) {
            inputField.setText("Error")
            clearInput()
        }
    }


    private fun updateInputField() {
        inputField.setText(currentInput)
    }

    private fun evaluateExpression(expr: String): Double {
        return object {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expr.length) expr[pos].toInt() else -1
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expr.length) throw RuntimeException("Invalid character: ${expr[pos]}")
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.toInt()) -> x += parseTerm()
                        eat('-'.toInt()) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.toInt()) -> x *= parseFactor()
                        eat('/'.toInt()) -> {
                            val divisor = parseFactor()
                            if (divisor == 0.0) throw ArithmeticException("Division by zero")
                            x /= divisor
                        }
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor()
                if (eat('-'.toInt())) return -parseFactor()

                var x = 0.0
                val startPos = pos
                while (ch >= '0'.toInt() && ch <= '9'.toInt()) nextChar()
                x = expr.substring(startPos, pos).toDouble()
                return x
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }
        }.parse()
    }
}
