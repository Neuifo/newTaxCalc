package com.neuifo.texcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText four_accumulation = findViewById(R.id.four_accumulation);
        four_accumulation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditText basicSalary = findViewById(R.id.basic_salary);
                    if (!TextUtils.isEmpty(basicSalary.getText().toString()) && TextUtils.isEmpty(((EditText) v).getText().toString())) {
                        ((EditText) v).setText(SalaryUtils.getFourSalary(Double.valueOf(basicSalary.getText().toString())));
                    }
                }
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra(SalaryUtils.MONTH_SALARY, getNum(R.id.basic_salary));
                intent.putExtra(SalaryUtils.FOUR_ACCUMULATION, getNum(R.id.four_accumulation));
                intent.putExtra(SalaryUtils.ALLOWANCE, getNum(R.id.other));
                startActivity(intent);
            }
        });
    }

    private int getNum(int id) {
        TextView viewById = findViewById(id);
        return Integer.valueOf(viewById.getText().toString());
    }
}
