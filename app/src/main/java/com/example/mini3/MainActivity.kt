package com.example.mini3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SimpleCalculator()


        }
    }
}


@Composable
fun SimpleCalculator() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Simple Calculator", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = input, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Result: $result", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "x"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "=" -> {
                                    try {
                                        result = calculateResult(input)
                                    } catch (e: Exception) {
                                        result = "Error"
                                    }
                                }
                                else -> input += label
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(label)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            input = ""
            result = ""
        }) {
            Text("Clear")
        }
    }
}

fun calculateResult(expr: String): String {
    return try {
        // Find operator (+, -, x, /)
        val operator = listOf("+", "-", "x", "/").firstOrNull { expr.contains(it) }
            ?: return expr

        val parts = expr.split(operator)
        if (parts.size != 2) return expr

        val num1 = parts[0].toDouble()
        val num2 = parts[1].toDouble()

        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "x" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> Double.NaN
        }
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}
