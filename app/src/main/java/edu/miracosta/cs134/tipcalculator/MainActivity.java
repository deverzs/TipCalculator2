package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    //instance variables
    //Bridge to model
    private Bill currentBill; //connection to model

    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;
    //for currency and percent from local device
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize (wire up) instanc vrs
        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById((R.id.percentTextView));
        percentSeekBar =findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);




        //set current tip percent
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);


        //don't need to send to view??

        //implement interface for EditText
        //inner class and instantiating an inner class with those methods
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //bill does the calculation
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //read  input from EditText and store in currentBill (Model)
               try{
                   double amount = Double.parseDouble(amountEditText.getText().toString());
                   //store in bill
                   currentBill.setAmount(amount);
                   calculteBill();
               }
               catch (NumberFormatException e)
               {
                   currentBill.setAmount(0.0);
               }
                calculteBill();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //SeekBar
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //update tip percent
                currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0); //convert to decimal
                //update view
                percentTextView.setText(percent.format(currentBill.getTipPercent()));
                calculteBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        // control-d to duplicate line

    }


    //tip and total should update
    //interface collection of empty methods
    public void calculteBill()
    {
       //tipTextView.setText(String.valueOf(currentBill.getTipAmount())); //won't format to currency
        //currency siimbol for local area
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText((percent.format(currentBill.getTipPercent())));
    }
}
