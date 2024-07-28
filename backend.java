package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private EditText inputText;
    private TextView displayText;
    private String currentOperator = "";
    private String display = "";
    private String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button deleteButton = findViewById(R.id.butdelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteNumber();
            }
        });

        inputText = findViewById(R.id.input_data);
        displayText = findViewById(R.id.result_data);
        Button clearbut = findViewById(R.id.clear);
        clearbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onClearButton();
            }
        });
    }

    private void appendToLast(String str) {
        inputText.getText().append(str);
    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        appendToLast(display);
        display = "";
    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        display += b.getText();
        if (endsWithOperator()) {
            replace(display);
        } else {
            appendToLast(display);
        }
        currentOperator = b.getText().toString();
        inputText.getText().append("");
        display = "";
    }

    public void onClearButton() {
        inputText.getText().clear();
        displayText.setText("");
        currentOperator = "";
    }

    public void deleteNumber() {
        if (getInput().length() > 0)
        {
            inputText.getText().delete(getInput().length() - 1, getInput().length());
        }
    }

    private String getInput()
    {

        return inputText.getText().toString();
    }

    private boolean endsWithOperator() {
        return getInput().endsWith("+") ||
                getInput().endsWith("-") ||
              getInput().endsWith("*") ||
                getInput().endsWith("/");
    }

    private void replace(String str)
    {

        inputText.getText().replace(getInput().length() - 1, getInput().length(), str);
    }

    public void Equalresult(View v) {
        String input = inputText.getText().toString();
        if (!input.isEmpty() && !endsWithOperator()) {
            try {
                double result = evaluateExpression(input);
                displayText.setText(String.valueOf(result));
            } catch (Exception e) {
                displayText.setText("math Error");
            }
        } else {
            displayText.setText("Invalid input");
        }
    }
    private double evaluateExpression(String expression) {
        String[] tokens = expression.split("(?=[-+*/%])|" +
                "(?<=[-+*/%])");
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double value = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += value;
                    break;
                case "-":
                    result -= value;
                    break;
                case "*":
                    result *=value;
                    break;
                case "/":
                    result /=value;
                    break;
                case "%":
                    result%=value;
                    break;
            }
        }

        return result;
    }
    }



