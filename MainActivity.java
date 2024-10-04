package com.example.expensetracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText editCategory, editAmount, editBudget;
    private Button addExpenseBtn, setBudgetBtn, viewSpendingBtn;
    private PieChart pieChart;
    private float budget = 0;
    private float totalSpending = 0;
    private HashMap<String, Float> expenses = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCategory = findViewById(R.id.editCategory);
        editAmount = findViewById(R.id.editAmount);
        editBudget = findViewById(R.id.editBudget);
        addExpenseBtn = findViewById(R.id.addExpenseBtn);
        setBudgetBtn = findViewById(R.id.setBudgetBtn);
        viewSpendingBtn = findViewById(R.id.viewSpendingBtn);
        pieChart = findViewById(R.id.pieChart);

        setBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetText = editBudget.getText().toString();
                if (!TextUtils.isEmpty(budgetText)) {
                    budget = Float.parseFloat(budgetText);
                    Toast.makeText(MainActivity.this, "Budget set to " + budget, Toast.LENGTH_SHORT).show();
                }
            }
        });

        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editCategory.getText().toString();
                String amountText = editAmount.getText().toString();

                if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(amountText)) {
                    float amount = Float.parseFloat(amountText);
                    totalSpending += amount;

                    if (expenses.containsKey(category)) {
                        expenses.put(category, expenses.get(category) + amount);
                    } else {
                        expenses.put(category, amount);
                    }

                    if (totalSpending > budget) {
                        Toast.makeText(MainActivity.this, "Warning: Budget Exceeded!", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(MainActivity.this, "Expense added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewSpendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSpending();
            }
        });
    }

    private void viewSpending() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String category : expenses.keySet()) {
            entries.add(new PieEntry(expenses.get(category), category));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh chart
    }
}
