package com.kbc19a21.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HelloListener listener = new HelloListener();

        Button btnOne = findViewById(R.id.one);
        Button btnPlus = findViewById(R.id.plus);
        Button btnEqual = findViewById(R.id.equal);
        Button btClear = findViewById(R.id.clear);
        btnOne.setOnClickListener(listener);
        btnPlus.setOnClickListener(listener);
        btnEqual.setOnClickListener(listener);
        btClear.setOnClickListener(listener);
    }

    private class HelloListener implements View.OnClickListener {
        List<String> formulaList = new ArrayList<>();

        public void onClick(View view) {

            TextView result = findViewById(R.id.result);
            TextView formula = findViewById(R.id.formula);

            int id = view.getId();
//            String tmp = "";
//            String tmpNum = "";
//            String tmpSymbol = "";
            String value = "";
            //idでの識別
            switch (id) {
                case R.id.one:
                    value = "1";
                    break;
                case R.id.plus:
                    value = "+";
                    break;
                case R.id.equal:
                    value = "=";
                    break;
            }

            formulaList = addTextFormula(formulaList, value);
            showFormula(formulaList, formula);

        }

    }

    public List<String> addTextFormula(List<String> formula, String tmp) {
        //数字が入力された場合
        if (isInt(tmp)) {
            if (formula.size() == 0) {
                //空の場合
                formula.add(tmp);
            } else if (isFourSymbol(formula.get(formula.size() - 1))) {
                //最後が記号の場合
                formula.add(tmp);
            } else if (isInt(formula.get(formula.size() - 1))) {
                //最後が数字の場合
                formula.set(formula.size() - 1, formula.get(formula.size() - 1) + tmp);
            }
        } else if (isFourSymbol(tmp)) {
            //+,-,✕,÷が入力された場合
            if (formula.size() == 0) {
                //空の場合
            } else if (isFourSymbol(formula.get(formula.size() - 1))) {
                //最後が記号の場合
            } else if (isInt(formula.get(formula.size() - 1))) {
                //最後が数字の場合
                formula.add(tmp);
            }
        } else if (isEqual(tmp)) {
            //=が入力された場合
            if (formula.size() >= 3 && formula.size() % 2 == 1) {
                //計算結果の表示
                formula.clear();
            }
        }

        return formula;
    }

    public void showFormula(List<String> formulaList, TextView fml) {
        String formula = "";
        for (String value : formulaList) {
            formula += value;
        }
        fml.setText(formula);
    }

    public boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFourSymbol(String symbol) {
        switch (symbol) {
            case "+":
            case "-":
            case "*":
            case "/":
                return true;
            default:
                return false;
        }
    }

    public boolean isEqual(String symbol) {
        switch (symbol) {
            case "=":
                return true;
            default:
                return false;
        }
    }

}