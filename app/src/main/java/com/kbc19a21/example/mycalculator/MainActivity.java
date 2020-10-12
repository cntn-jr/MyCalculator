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
        boolean flagEqual = false;
        boolean clearFlag = true;
        String rsWork = "";

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

            if (flagEqual) {
                for (; ; ) {
                    formulaList.remove(formulaList.size() - 1);
                    if (formulaList.isEmpty()) {
                        break;
                    }
                }
                if(canInt(Float.valueOf(rsWork))){
                    rsWork = String.valueOf((int)Math.floor(Double.valueOf(String.valueOf(rsWork))));
                }
                formulaList.add(rsWork);
                flagEqual = false;
            }

            formulaList = addTextFormula(formulaList, value, result, clearFlag);
            showFormula(formulaList, formula);

            //=が入力された場合
            if (value.equals("=")) {
                if (formulaList.size() >= 3 && formulaList.size() % 2 == 1) {
                    rsWork = showResult(formulaList, result);
                    flagEqual = true;
                    clearFlag = false;
                }
            }

        }

    }

    public List<String> addTextFormula(List<String> formulaList, String tmp, TextView result, boolean clearFlag) {
        //数字が入力された場合
        if (isInt(tmp)) {
            if (formulaList.size() == 0) {
                //空の場合
                formulaList.add(tmp);
            } else if (isFourSymbol(formulaList.get(formulaList.size() - 1))) {
                //最後が記号の場合
                formulaList.add(tmp);
            } else if (clearFlag && (isInt(formulaList.get(formulaList.size() - 1)) || isFloat(formulaList.get(formulaList.size() - 1)))) {
                //最後が数字の場合
                formulaList.set(formulaList.size() - 1, formulaList.get(formulaList.size() - 1) + tmp);
            }
        } else if (isFourSymbol(tmp)) {
            //+,-,✕,÷が入力された場合
            if (formulaList.size() == 0) {
                //空の場合
            } else if (isFourSymbol(formulaList.get(formulaList.size() - 1))) {
                //最後が記号の場合
            } else if (isInt(formulaList.get(formulaList.size() - 1)) || isFloat(formulaList.get(formulaList.size() - 1))) {
                //最後が数字の場合
                formulaList.add(tmp);
            }
        } else {
            //何もしない
        }

        return formulaList;
    }

    //式の途中を見せる
    public void showFormula(List<String> formulaList, TextView fml) {
        String formula = "";
        for (String value : formulaList) {
            formula += value;
            formula += " ";
        }
        fml.setText(formula);
    }

    //計算する
    public String showResult(List<String> formulaList, TextView rsText) {
        float work1, work2;
        String sbl,rs;
        work1 = Float.parseFloat(formulaList.get(0));
        work2 = Float.parseFloat(formulaList.get(2));
        sbl = formulaList.get(1);
        work1 = calc(work1, work2, sbl);
        for (int i = 4; i < formulaList.size(); i += 2) {
            work2 = Float.parseFloat(formulaList.get(i));
            sbl = formulaList.get(i + 1);
            work1 = calc(work1, work2, sbl);
        }
        //int型にできるのであれば
        if(canInt(work1)){
            rs = String.valueOf((int)Math.floor(Double.valueOf(String.valueOf(work1))));
        }else{
            rs = String.valueOf(work1);
        }
        //文字列に変換
        rsText.setText(rs);
        return String.valueOf(work1);
    }

    public float calc(float work1, float work2, String sbl) {
        switch (sbl) {
            case "+":
                work1 = work1 + work2;
                break;
            case "-":
                break;
        }
        return work1;
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

    public boolean canInt(float num){
        double work1 = Double.parseDouble(String.valueOf(num));
        double work2 = Math.floor(Double.parseDouble(String.valueOf(num)));
        if(work1 - work2 == 0){
            return  true;
        }
        return  false;
    }

}