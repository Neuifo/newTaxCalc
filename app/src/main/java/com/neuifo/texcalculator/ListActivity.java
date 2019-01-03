package com.neuifo.texcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuifo.texcalculator.model.MyAmount;
import com.neuifo.texcalculator.model.TaxResult;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    private MyAmount amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        int monthSalary = intent.getIntExtra(SalaryUtils.MONTH_SALARY, 0);
        int fourAccumulation = intent.getIntExtra(SalaryUtils.FOUR_ACCUMULATION, 0);
        int allowance = intent.getIntExtra(SalaryUtils.ALLOWANCE, 0);
        amount = new MyAmount(monthSalary, fourAccumulation, allowance);
        initRecycle();


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button return true; } return super.onOptionsItemSelected(item); }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(linearLayoutManager);
        ListAdapter listAdapter = new ListAdapter(getData());
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }


    private void setHead(String newTotalSalary, String oldTotalSalary, String
            newTotalTax, String oldTotalTax) {
        TextView newYearSalary = findViewById(R.id.new_year_salary);
        newYearSalary.setText("新收入:" + newTotalSalary);
        TextView oldYearSalary = findViewById(R.id.old_year_salary);
        oldYearSalary.setText("旧收入:" + oldTotalSalary);
        TextView newYearTax = findViewById(R.id.new_year_tax);
        newYearTax.setText("新税:" + newTotalTax);
        TextView oldYearTex = findViewById(R.id.old_year_tex);
        oldYearTex.setText("旧税:" + oldTotalTax);

        TextView left = findViewById(R.id.left_text);

        int include = Integer.valueOf(newTotalSalary) - Integer.valueOf(oldTotalSalary);

        left.setText(include > 0 ? "恭喜你，节省了" + include : "很遗憾，你需额外支出" + -include);
        left.setTextColor(include > 0 ? Color.parseColor("#d33d33") : Color.parseColor("#008577"));


    }

    public List<TaxResult> getData() {
        double newTotalSalary = 0;
        double oldTotalSalary = 0;
        double newTotalTax = 0;
        double oldTotalTax = 0;
        List<TaxResult> data = new ArrayList<>();
        if (amount.amount - amount.insurance - amount.other - 5000 <= 0) {//不交税
            for (int i = 0; i < 12; i++) {
                TaxResult taxResult = new TaxResult(0,
                        amount.amount - amount.insurance - amount.other,
                        0,
                        amount.amount - amount.insurance - amount.other);
                newTotalSalary += taxResult.amount;
                oldTotalSalary += taxResult.oldAmount;
                newTotalTax += taxResult.tax;
                oldTotalTax += taxResult.oldTax;
                data.add(taxResult);
            }
            setHead(SalaryUtils.formatDouble(newTotalSalary),
                    SalaryUtils.formatDouble(oldTotalSalary),
                    SalaryUtils.formatDouble(newTotalTax),
                    SalaryUtils.formatDouble(oldTotalTax)
            );
            return data;
        }

        double addTax = 0;//速减税额
        double oldTax = SalaryUtils.getOldLeftOverAmount(amount);
        double oldAmount = amount.amount - amount.insurance - oldTax;


        for (int i = 1; i < 13; i++) {
            double[] leftOver = SalaryUtils.getLeftOverAmount(amount, i);
            double tmp = new Double(i) * (amount.amount - amount.insurance - amount.other - 5000) * leftOver[0] - leftOver[1] - addTax;
            addTax = addTax + tmp;
            TaxResult taxResult = new TaxResult(tmp,
                    amount.amount - amount.insurance - tmp,
                    oldTax,
                    oldAmount);
            newTotalSalary += taxResult.amount;
            oldTotalSalary += taxResult.oldAmount;
            newTotalTax += taxResult.tax;
            oldTotalTax += taxResult.oldTax;
            data.add(taxResult);

        }
        setHead(SalaryUtils.formatDouble(newTotalSalary),
                SalaryUtils.formatDouble(oldTotalSalary),
                SalaryUtils.formatDouble(newTotalTax),
                SalaryUtils.formatDouble(oldTotalTax)
        );
        return data;
    }


    class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<TaxResult> data;

        public ListAdapter(List<TaxResult> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_salary, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TaxResult taxResult = data.get(position);
            TextView month = holder.itemView.findViewById(R.id.item_month);
            TextView newMonthSalary = holder.itemView.findViewById(R.id.new_month_salary);
            TextView oldMonthSalary = holder.itemView.findViewById(R.id.old_month_salary);
            TextView newMonthTax = holder.itemView.findViewById(R.id.new_month_tax);
            TextView oldMonthTax = holder.itemView.findViewById(R.id.old_month_tax);

            month.setText((position + 1) + "月");
            newMonthSalary.setText(SalaryUtils.formatDouble(taxResult.amount));
            oldMonthSalary.setText(SalaryUtils.formatDouble(taxResult.oldAmount));
            newMonthTax.setText(SalaryUtils.formatDouble(taxResult.tax));
            oldMonthTax.setText(SalaryUtils.formatDouble(taxResult.oldTax));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }


    }
}
