package com.example.calculatorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String input = "";
    private String operator = "";
    private double firstOperand = Double.NaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        // Set listeners for buttons
        setNumberButtonListeners();
        setOperatorButtonListeners();

        // Clear (C) button
        findViewById(R.id.cButton).setOnClickListener(v -> {
            input = "";
            resultTextView.setText("0");
        });

        // All Clear (AC) button
        findViewById(R.id.acButton).setOnClickListener(v -> {
            input = "";
            operator = "";
            firstOperand = Double.NaN;
            resultTextView.setText("0");
        });

        // Equals button
        findViewById(R.id.equalButton).setOnClickListener(v -> {
            if (!Double.isNaN(firstOperand) && !input.isEmpty()) {
                double secondOperand = Double.parseDouble(input);
                double result = calculateResult(firstOperand, secondOperand, operator);
                resultTextView.setText(String.valueOf(result));
                input = String.valueOf(result);
                operator = "";
                firstOperand = Double.NaN;
            }
        });
    }

    private void setNumberButtonListeners() {
        int[] numberButtons = {
                R.id.zeroButton, R.id.oneButton, R.id.twoButton, R.id.threeButton,
                R.id.fourButton, R.id.fiveButton, R.id.sixButton,
                R.id.sevenButton, R.id.eightButton, R.id.nineButton,
                R.id.dotButton
        };

        View.OnClickListener numberListener = v -> {
            Button button = (Button) v;
            input += button.getText().toString();
            resultTextView.setText(input);
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtons = {
                R.id.plusButton, R.id.minusButton, R.id.multiplyButton, R.id.divideButton
        };

        View.OnClickListener operatorListener = v -> {
            if (!input.isEmpty()) {
                firstOperand = Double.parseDouble(input);
                Button button = (Button) v;
                operator = button.getText().toString();
                input = "";
            }
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(operatorListener);
        }
    }

    private double calculateResult(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "×":
                return firstOperand * secondOperand;
            case "÷":
                if (secondOperand != 0) {
                    return firstOperand / secondOperand;
                } else {
                    resultTextView.setText("Error");
                    return 0;
                }
            default:
                return secondOperand;
        }
    }
}
