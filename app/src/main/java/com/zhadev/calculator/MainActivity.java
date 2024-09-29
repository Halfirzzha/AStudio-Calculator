package com.zhadev.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private double firstNumber;
    private String operator;
    private TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setNumberButtonListeners();
        setOperatorButtonListeners();
        setSpecialButtonListeners();
    }

    private void initializeViews() {
        screen = findViewById(R.id.screen);
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
            R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4,
            R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(this::onNumberButtonClick);
        }
    }

    private void onNumberButtonClick(View view) {
        Button button = (Button) view;
        String currentText = screen.getText().toString();
        String buttonText = button.getText().toString();

        if (!currentText.equals("0")) {
            screen.setText(new StringBuilder(currentText).append(buttonText).toString());
        } else {
            screen.setText(buttonText);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = {
            R.id.div, R.id.times, R.id.plus, R.id.minus
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(this::onOperatorButtonClick);
        }
    }

    private void onOperatorButtonClick(View view) {
        Button button = (Button) view;
        try {
            firstNumber = Double.parseDouble(screen.getText().toString());
        } catch (NumberFormatException e) {
            screen.setText("Error");
            return;
        }
        operator = button.getText().toString();
        screen.setText("0");
    }

    private void setSpecialButtonListeners() {
        findViewById(R.id.ac).setOnClickListener(view -> {
            firstNumber = 0;
            screen.setText("0");
        });

        findViewById(R.id.off).setOnClickListener(view -> screen.setVisibility(View.GONE));
        findViewById(R.id.on).setOnClickListener(view -> {
            screen.setVisibility(View.VISIBLE);
            screen.setText("0");
        });

        findViewById(R.id.del).setOnClickListener(view -> {
            String num = screen.getText().toString();
            if (num.length() > 1) {
                screen.setText(num.substring(0, num.length() - 1));
            } else {
                screen.setText("0");
            }
        });

        findViewById(R.id.point).setOnClickListener(view -> {
            if (!screen.getText().toString().contains(".")) {
                screen.setText(new StringBuilder(screen.getText().toString()).append(".").toString());
            }
        });

        findViewById(R.id.equal).setOnClickListener(view -> {
            double secondNum;
            try {
                secondNum = Double.parseDouble(screen.getText().toString());
            } catch (NumberFormatException e) {
                screen.setText("Error");
                return;
            }
            double result = calculateResult(secondNum);
            screen.setText(String.valueOf(result));
            firstNumber = result;
        });
    }

    private double calculateResult(double secondNum) {
        switch (operator) {
            case "/":
                return firstNumber / secondNum;
            case "X":
                return firstNumber * secondNum;
            case "+":
                return firstNumber + secondNum;
            case "-":
                return firstNumber - secondNum;
            default:
                return firstNumber;
        }
    }
}