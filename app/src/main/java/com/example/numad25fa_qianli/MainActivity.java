package com.example.numad25fa_qianli;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView t = findViewById(R.id.text1);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(v -> {
            t.setText("Name: Qian Li\nEmail: li.qian6@northeastern.edu");

            t.setGravity(Gravity.CENTER);
        });

        Button newPage = findViewById(R.id.button_calc);
        newPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuickCalc.class);
            startActivity(intent);
        });
    }

    public void openQuickCalc(View view) {
        Intent intent = new Intent(MainActivity.this, QuickCalc.class);
        startActivity(intent);
    }



}