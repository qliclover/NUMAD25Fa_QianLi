package com.example.numad25fa_qianli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuickCalc extends AppCompatActivity {
    TextView textView;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quick_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = (TextView) findViewById(R.id.btn_display);
        textView.setText("CALC");

        int[] buttonIds = new int[]{
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int id : buttonIds) {
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    if (textView.getText().toString().equals("CALC")) {
                        text = "";
                    }
                    text = text + b.getText().toString();
                    textView.setText(text);
                }
            });
        }

//         plus function
        Button btn_plus = (Button) findViewById(R.id.btn_p);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                text = text + b.getText().toString();
                textView.setText(text);
            }
        });

//        minus function
        Button btn_minus = (Button) findViewById(R.id.btn_m);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                text = text + b.getText().toString();
                textView.setText(text);
            }
        });

//        delect function
        Button btn_delect = (Button) findViewById(R.id.btn_x);
        btn_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    if (text.equals("")) {
                        textView.setText("CALC");
                    } else {
                        textView.setText(text);
                    }
                }
            }
        });

//        equal function
        Button btn_equal = (Button) findViewById(R.id.btn_e);
        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = evaluate (text);
                    text = String.valueOf(result);
                    textView.setText(text);
                } catch (Exception e) {
                    textView.setText("Error");
                    text = "";
                }
            }
        });
    }

//    calculation function
    private int evaluate(String input) throws Exception {
        input = input.trim();
        if (input.equals("")) {
            throw new Exception("Empty input");
        }

        int result = 0;
        int curVal = 0;
        int sign = 1;

    for (int i = 0; i < input.length(); i++) {
        char cur = input.charAt(i);
        if (Character.isDigit(cur)) {
            curVal = curVal * 10 + (cur - '0');
        } else if (cur == '+') {
            result += sign * curVal;
            curVal = 0;
            sign = 1;
        } else if (cur == '-') {
            result += sign * curVal;
            curVal = 0;
            sign = -1;
        } else {
            throw new Exception("Invalid value: " + cur);
        }
    }
    result += sign * curVal;
    return result;
    }

}