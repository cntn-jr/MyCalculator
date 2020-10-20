package com.kbc19a21.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HelloListener listener = new HelloListener();

        Button btnZero = findViewById(R.id.zero);
        Button btnOne = findViewById(R.id.one);
        Button btnTwo = findViewById(R.id.two);
        Button btnThree = findViewById(R.id.three);
        Button btnFour = findViewById(R.id.four);
        Button btnFive = findViewById(R.id.five);
        Button btnSix = findViewById(R.id.six);
        Button btnSeven = findViewById(R.id.seven);
        Button btnEight = findViewById(R.id.eight);
        Button btnNine = findViewById(R.id.nine);
        Button btnPlus = findViewById(R.id.plus);
        Button btnMinus = findViewById(R.id.minus);
        Button btnTimes = findViewById(R.id.times);
        Button btnDevide = findViewById(R.id.divided);
        Button btnEqual = findViewById(R.id.equal);
        Button btClear = findViewById(R.id.clear);
        btnOne.setOnClickListener(listener);
        btnTwo.setOnClickListener(listener);
        btnThree.setOnClickListener(listener);
        btnFour.setOnClickListener(listener);
        btnFive.setOnClickListener(listener);
        btnSix.setOnClickListener(listener);
        btnSeven.setOnClickListener(listener);
        btnEight.setOnClickListener(listener);
        btnNine.setOnClickListener(listener);
        btnZero.setOnClickListener(listener);
        btnPlus.setOnClickListener(listener);
        btnMinus.setOnClickListener(listener);
        btnTimes.setOnClickListener(listener);
        btnDevide.setOnClickListener(listener);
        btnEqual.setOnClickListener(listener);
        btClear.setOnClickListener(listener);
    }

    private class HelloListener implements View.OnClickListener {
        List<String> formulaList = new ArrayList<>();
        String rsWork = "";

        public void onClick(View view) {

            TextView result = findViewById(R.id.result);
            TextView formula = findViewById(R.id.formula);

            int id = view.getId();
            String value = "";
            //idでの識別
            switch (id) {
                case R.id.zero:
                    value = "0";
                    break;
                case R.id.one:
                    value = "1";
                    break;
                case R.id.two:
                    value = "2";
                    break;
                case R.id.three:
                    value = "3";
                    break;
                case R.id.four:
                    value = "4";
                    break;
                case R.id.five:
                    value = "5";
                    break;
                case R.id.six:
                    value = "6";
                    break;
                case R.id.seven:
                    value = "7";
                    break;
                case R.id.eight:
                    value = "8";
                    break;
                case R.id.nine:
                    value = "9";
                    break;
                case R.id.plus:
                    value = "+";
                    break;
                case R.id.minus:
                    value = "-";
                    break;
                case R.id.times:
                    value = "*";
                    break;
                case R.id.divided:
                    value = "/";
                    break;
                case R.id.equal:
                    value = "=";
                    break;
                case R.id.clear:
                    value = "clear";
                    break;
            }

            //=が入力された場合
            if (value.equals("=")) {
                if (formulaList.size() >= 3 && formulaList.size() % 2 == 1) {
                    rsWork = showResult(formulaList, result);
                    for (; ; ) {
                        formulaList.remove(formulaList.size() - 1);
                        if (formulaList.isEmpty()) {
                            break;
                        }
                    }
                    formulaList.add(rsWork);
                }
            }

            //クリアボタン
            if (value.equals("clear")){
                for (; ; ) {
                    formulaList.remove(formulaList.size() - 1);
                    if (formulaList.isEmpty()) {
                        break;
                    }
                }
                result.setText("");
                formula.setText("");
            }

            formulaList = addTextFormula(formulaList, value, result);
            showFormula(formulaList, formula);

        }

    }

    public List<String> addTextFormula(List<String> formulaList, String tmp, TextView result) {
        //数字が入力された場合
        if (isInt(tmp)) {
            if (formulaList.size() == 0) {
                //空の場合
                formulaList.add(tmp);
            } else if (isFourSymbol(formulaList.get(formulaList.size() - 1))) {
                //最後が記号の場合
                formulaList.add(tmp);
            } else if ((isInt(formulaList.get(formulaList.size() - 1)) || isFloat(formulaList.get(formulaList.size() - 1)))) {
                //最後が数字の場合
                if(formulaList.size() > 2 || (formulaList.size() == 1 && result.getText().equals(""))){
                    formulaList.set(formulaList.size() - 1, formulaList.get(formulaList.size() - 1) + tmp);
                }
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
        BigDecimal work1, work2;
        String sbl, rs;
        work1 = stringToBd(formulaList.get(0));
        work2 = stringToBd(formulaList.get(2));
        sbl = formulaList.get(1);
        work1 = calc(work1, work2, sbl);
        for (int i = 3; i < formulaList.size(); i += 2) {
            sbl = formulaList.get(i);
            work2 = stringToBd(formulaList.get(i + 1));
            work1 = calc(work1, work2, sbl);
        }
        rs = String.valueOf(work1);
        //int型にできるのであれば
        if (canInt(work1)) {
            rs = String.valueOf((int) Math.floor(Double.valueOf(String.valueOf(work1))));
        } else {
            rs = String.valueOf(work1);
        }
        //文字列に変換
        rsText.setText(rs);
        return rs;
    }

    public BigDecimal calc(BigDecimal work1, BigDecimal work2, String sbl) {
        switch (sbl) {
            case "+":
                work1 = work1.add(work2);
                break;
            case "-":
                work1 = work1.subtract(work2);
                break;
            case "*":
                work1 = work1.multiply(work2);
                break;
            case "/":
                work1 = work1.divide(work2, 10, BigDecimal.ROUND_HALF_UP);
//                work1 = work1.divide(work2);
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

    public boolean canInt(BigDecimal num) {
        double w;
        BigDecimal z= new BigDecimal("0.0");
        BigDecimal work1 = num;
        w = Math.floor(Double.parseDouble(String.valueOf(num)));
        BigDecimal work2 = BigDecimal.valueOf(w);
        BigDecimal w3 = work1.subtract(work2);
        if (w3 .compareTo(z) == 0) {
            return true;
        }
        return false;
    }

    public BigDecimal stringToBd(String num) {
        return BigDecimal.valueOf(Double.parseDouble(num));
    }

}