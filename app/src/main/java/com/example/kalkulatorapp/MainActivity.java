package com.example.kalkulatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

class EmptyDataException extends Exception {}
class DivideByZeroException extends Exception{}
class NegativeNumberException extends Exception{}

public class MainActivity extends AppCompatActivity {

    TextView textCalculations, textOutput;
    Double savedNumber, currentNumber;
    String flag, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCalculations = findViewById(R.id.textCalculations);
        textOutput = findViewById(R.id.textOutput);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(textCalculations != null)outState.putString("keyCalculations", String.valueOf(textCalculations.getText()));
        if(textOutput != null) outState.putString("keyOutput", String.valueOf(textOutput.getText()));
        if(flag != null)outState.putString("keyFlag", flag);
        if(savedNumber != null)outState.putDouble("keySavedNumber", savedNumber);
        if(currentNumber != null)outState.putDouble("keyCurrentNumber", currentNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String variableCalculations = savedInstanceState.getString("keyCalculations", "0");
        String variableOutput = savedInstanceState.getString("keyOutput", "0");
        String variableFlag = savedInstanceState.getString("keyFlag", "0");
        Double variableSavedNumber = savedInstanceState.getDouble("keySavedNumber");
        Double variableCurrentNumber = savedInstanceState.getDouble("keyCurrentNumber");
        textCalculations.setText(variableCalculations);
        textOutput.setText(variableOutput);
        flag = variableFlag;
        savedNumber = variableSavedNumber;
        currentNumber = variableCurrentNumber;
    }

    public void doMathematicalOperation(View view) throws EmptyDataException {
        try {
            if ((textOutput.getText().equals("") && textCalculations.getText().equals("")) || textOutput.getText().equals("")) throw new EmptyDataException();

            String strStorage = String.valueOf(textOutput.getText());
            if(String.valueOf(textCalculations.getText()).contains("=")){
                textCalculations.setText("");           // Obecny kod znajduje się przed poniższym ifem żeby nie zmylić występowaniem historii obliczeń, która i tak jest usuwana
                savedNumber = Double.parseDouble(String.valueOf(textOutput.getText()));
            }

            if(!(view.getId() == R.id.buttonDecimalLogarithm || view.getId() == R.id.buttonFactorial || view.getId() == R.id.buttonSqrt || view.getId() == R.id.buttonPower2 || view.getId() == R.id.buttonPower3)) {

                if (!String.valueOf(textCalculations.getText()).isEmpty()) {
                    String[] strArray = String.valueOf(textCalculations.getText()).split(" ");
                    int size = strArray.length;

                    if (strStorage.contains("Log10")) {
                        currentNumber = calculations("Log10", currentNumber, null);
                    } else if (strStorage.contains("!")) {
                        currentNumber = calculations("!", currentNumber, null);
                    } else if (strStorage.contains("SQRT")) {
                        currentNumber = calculations("sqrt", currentNumber, null);
                    } else if (strStorage.contains("^2")) {
                        currentNumber = calculations("^2", currentNumber, null);
                    } else if (strStorage.contains("^3")) {
                        currentNumber = calculations("^3", currentNumber, null);
                    } else {
                        currentNumber = Double.parseDouble(String.valueOf(textOutput.getText()));
                        flag = "";
                    }
                    savedNumber = calculations(strArray[size - 1], savedNumber, currentNumber);
                } else {
                    if (strStorage.contains("Log10") || strStorage.contains("!") || strStorage.contains("SQRT") || strStorage.contains("^2") || strStorage.contains("^3")) {
                        currentNumber = calculations(flag, currentNumber, null);
                    }else {
                        currentNumber = Double.parseDouble(String.valueOf(textOutput.getText()));
                        flag = "";
                    }
                    savedNumber = currentNumber;
                }
            }
            else{
                currentNumber = Double.parseDouble(String.valueOf(textOutput.getText()));
            }
           switch(view.getId()) {
               case R.id.buttonAddition:
                   textOutput.setText("");
                   message = textCalculations.getText() + currentNumber.toString() + " + ";
                   textCalculations.setText(message);
                   break;
               case R.id.buttonSubtraction:
                   textOutput.setText("");
                   message = textCalculations.getText() + currentNumber.toString() + " - ";
                   textCalculations.setText(message);
                   break;
               case R.id.buttonMultiplication:
                   textOutput.setText("");
                   message = textCalculations.getText() + currentNumber.toString() + " * ";
                   textCalculations.setText(message);
                   break;
               case R.id.buttonDivision:
                   textOutput.setText("");
                   message = textCalculations.getText() + currentNumber.toString() + " / ";
                   textCalculations.setText(message);
                   break;
               case R.id.buttonModulo:
                   textOutput.setText("");
                   message = textCalculations.getText() + currentNumber.toString() + " % ";
                   textCalculations.setText(message);
                   break;
               case R.id.buttonSqrt:
                   flag = "sqrt";
                   message = "SQRT(" + currentNumber + ")";
                   textOutput.setText(message);
                   break;
               case R.id.buttonPower2:
                   flag = "^2";
                   message = currentNumber.toString() + "^2";
                   textOutput.setText(message);
                   break;
               case R.id.buttonPower3:
                   flag = "^3";
                   message = currentNumber.toString() + "^3";
                   textOutput.setText(message);
                   break;
               case R.id.buttonFactorial:
                   flag = "!";
                   message = currentNumber.toString() + "!";
                   textOutput.setText(message);
                   break;
               case R.id.buttonDecimalLogarithm:
                   flag = "Log10";
                   message = "Log10(" + currentNumber + ")";
                   textOutput.setText(message);
                   break;
               case R.id.buttonEqual:
                   if (String.valueOf(textCalculations.getText()).equals("") && (flag.equals("Log10") || flag.equals("!") || flag.equals("sqrt") || flag.equals("^2") || flag.equals("^3"))) {
                       message = textCalculations.getText() + String.valueOf(textOutput.getText()) + " =";
                       textCalculations.setText(message);
                   }else {
                       message = textCalculations.getText() + currentNumber.toString() + " =";
                       textCalculations.setText(message);
                   }
                   message = Double.toString(savedNumber);
                   textOutput.setText(message);
                   break;
           }
       }catch(EmptyDataException e){
           Toast.makeText(getApplicationContext(), "Wprowadz liczbe", Toast.LENGTH_SHORT).show();
       }catch(DivideByZeroException d){
           Toast.makeText(getApplicationContext(), "Nie mozna dzielic przez zero", Toast.LENGTH_SHORT).show();
       }catch(NegativeNumberException n){
           Toast.makeText(getApplicationContext(), "Liczba musi byc naturalna", Toast.LENGTH_SHORT).show();
       }
        catch(IllegalArgumentException i){
            System.out.println("Niepoprawna operacja");
        }
    }
    public void changeSign(View view) throws EmptyDataException {
        try {
            if (textOutput.getText().equals("")) throw new EmptyDataException();
            String strStorage = String.valueOf(textOutput.getText());
            if (!(strStorage.contains("Log10") || strStorage.contains("!") || strStorage.contains("SQRT") || strStorage.contains("^2") || strStorage.contains("^3"))) {
                Double tmp = Double.parseDouble(String.valueOf(textOutput.getText()));
                tmp = - tmp;
                message = tmp.toString();
                textOutput.setText(message);
            }
            }catch(EmptyDataException e){
                Toast.makeText(getApplicationContext(), "Wprowadz liczbe", Toast.LENGTH_LONG).show();
            }

    }

    public void makePoint(View view) throws EmptyDataException {
        try {
            if (textOutput.getText().equals("")) throw new EmptyDataException();
            String str = String.valueOf(textOutput.getText());
            int tmp = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '.') {
                    tmp = 1;
                }
            }
            if (tmp == 0) {
                message = textOutput.getText() + ".";
                textOutput.setText(message);
            }
        }catch(EmptyDataException e){
            Toast.makeText(getApplicationContext(), "Wprowadz liczbe", Toast.LENGTH_SHORT).show();
        }
    }

    public void clean(View view) {
        textOutput.setText("");
        textCalculations.setText("");
    }


    public void EnterNumber(View view) throws IllegalStateException
    {
        if(String.valueOf(textCalculations.getText()).contains("=")){
            textCalculations.setText("");
            savedNumber = Double.parseDouble(String.valueOf(textOutput.getText()));
        }
        String strStorage = String.valueOf(textOutput.getText());
        if(strStorage.equals("0")) textOutput.setText("");

        if(!(strStorage.contains("Log10") || strStorage.contains("!") || strStorage.contains("SQRT") || strStorage.contains("^2") || strStorage.contains("^3")) || view.getId() == R.id.buttonChangeSign) {
            switch (view.getId()) {
                case R.id.button0:
                    message = textOutput.getText() + "0";
                    textOutput.setText(message);
                    break;
                case R.id.button1:
                    message = textOutput.getText() + "1";
                    textOutput.setText(message);
                    break;
                case R.id.button2:
                    message = textOutput.getText() + "2";
                    textOutput.setText(message);
                    break;
                case R.id.button3:
                    message = textOutput.getText() + "3";
                    textOutput.setText(message);
                    break;
                case R.id.button4:
                    message = textOutput.getText() + "4";
                    textOutput.setText(message);
                    break;
                case R.id.button5:
                    message = textOutput.getText() + "5";
                    textOutput.setText(message);
                    break;
                case R.id.button6:
                    message = textOutput.getText() + "6";
                    textOutput.setText(message);
                    break;
                case R.id.button7:
                    message = textOutput.getText() + "7";
                    textOutput.setText(message);
                    break;
                case R.id.button8:
                    message = textOutput.getText() + "8";
                    textOutput.setText(message);
                    break;
                case R.id.button9:
                    message = textOutput.getText() + "9";
                    textOutput.setText(message);
                    break;
            }
        }
    }

    public Double calculations(String opcion, Double n1, Double n2) throws DivideByZeroException, NegativeNumberException {
        Double result = 0.0;
        switch(opcion){
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "*":
                result = n1 * n2;
                break;
            case "/":
                if(n2 == 0.0) throw new DivideByZeroException();
                result = n1 / n2;
                break;
            case "sqrt":
                result = Math.sqrt(n1);
                break;
            case "^2":
                result = n1 * n1;
                break;
            case "^3":
                result = n1 * n1 * n1;
                break;
            case "Log10":
                if(n1 < 0) throw new NegativeNumberException();
                result = Math.log10(n1);
                break;
            case "%":
                result = n1 % n2;
                break;
            case "!":
                if(n1 < 0.0 || (!n1.toString().endsWith(".0") && n1.toString().contains("."))) throw new NegativeNumberException();
                result = doFactorial(n1);
                break;
        }
        return result;
    }

    public Double doFactorial(Double n)
    {
        if (n < 1)
            return 1.0;
        else
            return n * doFactorial(n - 1);
    }

}
