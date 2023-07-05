package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    StringBuilder num1 = new StringBuilder("");

    StringBuilder num2 = new StringBuilder("");

    String operator = "";

    boolean isFirstNumber = true;//是否是第一个数字

    boolean textView2HasResult = false;

    boolean noPoint = true;//判断是否有小数点

    boolean allowSignal = true;//只允许输入一个符号

    String currentOperator = null;

    StringBuilder details = new StringBuilder("");

    TextView textView2 ;

    //获取按钮和设置监听
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取所有的按钮
        Button b0 = (Button) findViewById(R.id.num0);
        Button b1 = (Button) findViewById(R.id.num1);
        Button b2 = (Button) findViewById(R.id.num2);
        Button b3 = (Button) findViewById(R.id.num3);
        Button b4 = (Button) findViewById(R.id.num4);
        Button b5 = (Button) findViewById(R.id.num5);
        Button b6 = (Button) findViewById(R.id.num6);
        Button b7 = (Button) findViewById(R.id.num7);
        Button b8 = (Button) findViewById(R.id.num8);
        Button b9 = (Button) findViewById(R.id.num9);
        Button pointL = (Button) findViewById(R.id.pointL);
        Button clear = (Button) findViewById(R.id.clr);
        Button delete = (Button) findViewById(R.id.delete);
        Button changeS = (Button) findViewById(R.id.changeS);
        Button rooting = (Button) findViewById(R.id.rooting);
        Button add = (Button) findViewById(R.id.add);
        Button minus = (Button) findViewById(R.id.minus);
        Button multiple = (Button) findViewById(R.id.multiply);
        Button divide = (Button) findViewById(R.id.divide);
        Button equal = (Button) findViewById(R.id.equal);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        //为所有按键设置监听事件

        //数字
        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        textView2.setOnClickListener(this);

        //符号
        pointL.setOnClickListener(this);
        clear.setOnClickListener(this);
        delete.setOnClickListener(this);
        changeS.setOnClickListener(this);
        rooting.setOnClickListener(this);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiple.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
    }

    // 具体事件
    // 点击后的事件
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        TextView textView1 = (TextView) findViewById(R.id.textView1);//输入框
        TextView textView2 = (TextView) findViewById(R.id.textView2);//输出框

        int id = view.getId();

        // 1.数字或小数点
        if (id == R.id.num1 || id == R.id.num0 || id == R.id.num2 || id == R.id.num3 || id == R.id.num4 || id == R.id.num5
                || id == R.id.num6
                || id == R.id.num7
                || id == R.id.num8
                || id == R.id.num9
                || id == R.id.pointL) {
            handleNum(button, textView1);
            return;
        }

        // 2.符号
        if (id == R.id.add
                || id == R.id.minus
                || id == R.id.multiply
                || id == R.id.divide) {
            operateChar(button, textView1,textView2);
            return;
        }

        // 3.清空
        if (id == R.id.clr) {
            clear(textView1, textView2);
            return;
        }

        //4.回退
        if (id == R.id.delete) {
            handleDel(textView1);
            return;
        }

        //5.正负号
        if (id == R.id.changeS) {
            //handleS(textView1);
            change(textView1);
            return;
        }

        //6.等于号
        if (id == R.id.equal) {
            handleEquals(textView2);
            return;
        }

        //7.开根号
        if (id == R.id.rooting) {
            sqrtCount(textView1);
            return;
        }

    }

    public void handleNum(Button button, TextView textView1) {

        if(details.length()!=0){
            if(details.charAt(0)=='N'){
                details=new StringBuilder("");
            }
        }
        String numString = button.getText().toString();

        if (isFirstNumber) {
            if (numString.equals(".")) {
                if (noPoint) {
                    num1.append(numString);
                    details.append(numString);//加入到详情页和数字页
                    noPoint = false;
                }
            } else {
                num1.append(numString);
                details.append(numString);//加入到详情页和数字页
            }
        } else {
            if (numString.equals(".")) {
                if (noPoint) {
                    num2.append(numString);
                    details.append(numString);//加入到详情页和数字页
                    noPoint = false;
                }
            } else {
                num2.append(numString);
                details.append(numString);
            }
        }

        //显示在TextView1
        textView1.setText(details);
    }

    public void clear(TextView textView1, TextView textView2) {
        //重置数值
        num1.setLength(0);
        num2.setLength(0);
        operator = "";
        details.setLength(0);
        textView1.setText(details);
        textView2.setText(details);
        allowSignal = true;
        isFirstNumber = true;
        noPoint = true;
        textView2HasResult = false;
    }

    public void operateChar(Button button, TextView textView1,TextView textView2) {
        if(textView2HasResult){
            allowSignal = true;
            StringBuilder stringBuilder = new StringBuilder(textView2.getText().toString());
            num1 = stringBuilder;
            num2=new StringBuilder("");

        }
        if (allowSignal) {
            if(details.length()!=0){
                if(details.charAt(0)=='N'){
                    num1.setLength(0);
                    num2.setLength(0);
                    operator = "";
                    details.setLength(0);
                    textView1.setText(details);
                    allowSignal = true;
                    isFirstNumber = true;
                }
            }
            noPoint = true; //可以重新输入小数点
            String op = button.getText().toString();
            operator = op;
            currentOperator = op;
            isFirstNumber = false;//按到符号，表示第一个数字结束
            details.append(currentOperator);
            textView1.setText(details);
            allowSignal = false;
            //显示在TextView1
            //textView1.setText(details);
        }
    }

    public void handleEquals(TextView textView2) {

        //重置输出区域
        textView2.setText("");

        double number1;
        double number2;
        try {
            number1 = Double.parseDouble(String.valueOf(num1));
        } catch (Exception e) {
            number1 = 0;
        }

        try {
            number2 = Double.parseDouble(String.valueOf(num2));
        } catch (Exception e) {
            number2 = 0;
        }


        double result = 0.0;
        switch (operator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;
                break;
            default:
                result = number1;
                break;
        }
        textView2HasResult=true;
        textView2.setText(String.valueOf(result));
    }

    public void handleDel(TextView textView1) {

        if (details.length() != 0) {
            if(details.charAt(0)=='N'){
                details=new StringBuilder("");
                return;
            }
            char c = details.charAt(details.length() - 1);

            if (c == '+' || c == '-' || c == '*' || c == '/') {
                allowSignal = true;//允许输入符号
                operator = "";
                isFirstNumber = true;
            } else if(c == '.'){
                noPoint = true;
            } else if (c==')') {
                //遇到),删除左括号和右括号
                details.deleteCharAt(0);
                details.deleteCharAt(details.length()-1);
            } else {
                if (isFirstNumber) {
                    num1.deleteCharAt(num1.length() - 1);
                } else {
                    try{
                        if(num2.length()!=0){
                            num2.deleteCharAt(num2.length() - 1);
                        }else {

                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            details.deleteCharAt(details.length() - 1);
        }
        textView1.setText(details);
    }

    // TODO 变正负结合删除操作有点毛病   解决
    public void change(TextView textView1){
            if(details.length()==0){
                Toast.makeText(MainActivity.this,"没有数字",Toast.LENGTH_SHORT).show();
                return;
            }
            if(details.charAt(details.length()-1)=='+'||details.charAt(details.length()-1)=='-'||details.charAt(details.length()-1)=='*'||details.charAt(details.length()-1)=='/'){
                Toast.makeText(MainActivity.this,"请先删除符号",Toast.LENGTH_SHORT).show();
                return;
            }
            details.setLength(0);

            if (isFirstNumber) {
                char c = num1.charAt(0);
                if (c == '-') {
                    num1.deleteCharAt(0); // 删除首字符 '-'
                    details.append(num1);
                } else {
                    num1.insert(0, '-'); // 在首字符前插入 '-'
                    details.append('(').append(num1).append(')');
                }

                details.append(operator).append(num2);
            } else {
                details.append(num1).append(operator);

                char c = num2.charAt(0);
                if (c == '-') {
                    num2.deleteCharAt(0); // 删除首字符 '-'
                    details.append(num2);
                } else {
                    num2.insert(0, '-'); // 在首字符前插入 '-'
                    details.append('(').append(num2).append(')');
                }
            }

            textView1.setText(details);
    }
    // TODO 开根号     解决
    private void sqrtCount(TextView textView1){

        //将当前的算式结果算出来，再开根号
        double number1;
        double number2;
        try {
            number1 = Double.parseDouble(String.valueOf(num1));
        } catch (Exception e) {
            number1 = 0;
        }

        try {
            number2 = Double.parseDouble(String.valueOf(num2));
        } catch (Exception e) {
            number2 = 0;
        }


        double result = 0.0;
        switch (operator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;
                break;
            default:
                result = number1;
                break;
        }
        result = Math.sqrt(result);

        num1 = new StringBuilder("");
        num1.append(result);
        details=new StringBuilder("");
        details.append(num1);
        num2=new StringBuilder("0");

        textView1.setText(String.valueOf(result));
    }
}


