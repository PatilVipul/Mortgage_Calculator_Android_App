package com.example.vipul.mortgagecalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import static java.lang.StrictMath.pow;

public class MainActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener
{
    private SeekBar interestRate_SeekBar;
    private TextView interestRate_TextView;
    private EditText amountBorrowed_EditText;
    private RadioButton radioButton7;
    private RadioButton radioButton15;
    private RadioButton radioButton30;
    private CheckBox taxes_CheckBox;
    private TextView payment_TextView;
    private Button calculate_Button;
    private RadioGroup radio_Group;

    double principal,interest,result=0.0,taxes=0.0;
    int number_of_months=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interestRate_SeekBar = (SeekBar) findViewById(R.id.interestRate_SeekBar);
        interestRate_SeekBar.setOnSeekBarChangeListener(this);
        interestRate_SeekBar.setMax(100);

        interestRate_TextView = (TextView) findViewById(R.id.interestRate_TextView);

        amountBorrowed_EditText = (EditText) findViewById(R.id.amountBorrowed_EditText);

        radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton15 = (RadioButton) findViewById(R.id.radioButton15);
        radioButton30 = (RadioButton) findViewById(R.id.radioButton30);

        radio_Group = (RadioGroup) findViewById(R.id.radioGroup);

        taxes_CheckBox = (CheckBox) findViewById(R.id.TaxesCheckBox);

        payment_TextView = (TextView) findViewById(R.id.paymentTextView);

        calculate_Button = (Button) findViewById(R.id.calculateButton);
        calculate_Button.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        interestRate_TextView.setText(""+(interestRate_SeekBar.getProgress()/10.0)+"%");
    }

    @Override
    public void onClick(View v)
    {

        if(amountBorrowed_EditText.getText().length()==0)
            Toast.makeText(getApplicationContext(), "Please enter the amount borrowed", Toast.LENGTH_SHORT).show();
        else
        {
            principal = Double.parseDouble(amountBorrowed_EditText.getText().toString());
            interest = (interestRate_SeekBar.getProgress() / 10.0) / 1200;

            if(radioButton7.isChecked())
                number_of_months = 7 * 12;
            else if(radioButton15.isChecked())
                number_of_months = 15 * 12;
            else if(radioButton30.isChecked())
                number_of_months = 30 * 12;
            else
                Toast.makeText(getApplicationContext(), "Please select the loan term", Toast.LENGTH_SHORT).show();

            if (taxes_CheckBox.isChecked())
                taxes = (0.1 * (principal)) / 100;
            else
                taxes = 0;

            if (interest != 0)
            {
                double value = pow((1 + interest), -number_of_months);
                result = (principal * (interest / (1 - value))) + taxes;
            }
            else
                result = (principal / number_of_months) + taxes;
        }

        payment_TextView.setText("Your monthly payment: ");

        if(amountBorrowed_EditText.getText().length()==0 || radio_Group.getCheckedRadioButtonId() == -1)
            payment_TextView.append("0");
        else
            payment_TextView.append(new DecimalFormat("##.##").format(result));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
    }
}