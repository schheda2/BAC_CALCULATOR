package com.example.himanshu.baccalculator;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    EditText weight;
    TextView alcohol_value;
    TextView BAC_Level;
    TextView final_status;
    int indicator;
    SeekBar alcohol_percentage;
    int entered_weight=0;
    Button save,add_drink, reset;
    private RadioGroup rg;
    private Switch gender;
    double r=0.68,gender_value;
    double alcohol_consumed;
    double final_alcohol_consumed_value;
    double A;
    double BAC;
    double temp_alcohol=0;
    int ounces;
   ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight=(EditText)findViewById(R.id.weight_entry);
         alcohol_value=(TextView)findViewById(R.id.textView_indicator);

        save=(Button)findViewById(R.id.button_save);
        save.setOnClickListener(this);

        add_drink=(Button)findViewById(R.id.button_addDrink);
        add_drink.setOnClickListener(this);

        reset=(Button)findViewById(R.id.button_reset);
        reset.setOnClickListener(this);

        alcohol_percentage = (SeekBar)findViewById(R.id.seekBar);
        alcohol_percentage.setProgress(5);
        alcohol_value.setText(String.valueOf(5));
        alcohol_percentage.setOnSeekBarChangeListener(this);

progressBar=(ProgressBar)findViewById(R.id.progressBar1);



        rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
            }
        });

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progress=((int)Math.round(progress/5))*5;
        indicator=progress;
        alcohol_value.setText(String.valueOf(indicator));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onSwitchClicked(View view)
    {
        gender=(Switch)findViewById(R.id.switch_gender);
        if(gender.isChecked())
        {
            r=0.55;
        }
        else
        {
            r=0.68;
        }
    }

    @Override
    public void onClick(View v) {
int id=v.getId();
        if(id==R.id.button_save)
        {
            if(weight.getText().toString()!="")
                {
                    entered_weight=Integer.valueOf(weight.getText().toString());
                    gender_value=r;
                    Log.d("demo",String.valueOf(gender_value));
                    Log.d("demo",String.valueOf(entered_weight));

                }
            else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the weight value",Toast.LENGTH_LONG).show();
                }

        }
        else if(id==R.id.button_addDrink)
        {

            if(entered_weight==0)
            {

                Toast.makeText(getApplicationContext(),"Enter the weight in lbs and then save",Toast.LENGTH_LONG).show();
            }
            else
            {
            if(weight.getText().toString()!="")
            {
                if(rg.getCheckedRadioButtonId()==R.id.radioButton_1oz)
                {
                    ounces=1;
                }
                else if(rg.getCheckedRadioButtonId()==R.id.radioButton_5oz)
                {
                    ounces=5;
                }
                else
                {
                    ounces=12;
                }

                Log.d("demo",String.valueOf(ounces));
                Log.d("demo",alcohol_value.getText().toString());
                int temp=Integer.parseInt(alcohol_value.getText().toString());

                A=temp/100.0;
                alcohol_consumed=ounces*A;
                temp_alcohol=temp_alcohol+alcohol_consumed;

                Log.d("demo","Consumed alcohol"+String.valueOf(alcohol_consumed));
                BAC=((temp_alcohol*6.24)/(entered_weight*gender_value));
                Log.d("demo","BAC Value is"+String.valueOf(BAC));

                BAC_Level=(TextView)findViewById(R.id.textView_BACLevel);
                BAC_Level.setText("BAC Level: "+String.valueOf(BAC));
                final_status=(TextView)findViewById(R.id.textView_statusResult);
                Log.d("demo","final_consumed_value"+String.valueOf(BAC));
                progressBar.setProgress((int)BAC*100);

                if(BAC<=0.08)
                {
                    final_status.setBackgroundColor(Color.parseColor("#33cc33"));
                    final_status.setText("You're safe");
                }
                else if(BAC>0.08 && BAC<0.20)
                {
                    final_status.setBackgroundColor(Color.parseColor("#ff8533"));
                    final_status.setText("Be careful!!");
                }
                else if(BAC>=0.20)
                {
                    final_status.setBackgroundColor(Color.parseColor("#ff0000"));
                    final_status.setText("Over the limit");
                    Toast.makeText(getApplicationContext(),"No more drinks for you!!",Toast.LENGTH_LONG).show();
                    add_drink.setEnabled(false);
                    save.setEnabled(false);
                    BAC_Level.setText("");

                }
                else
                {
                    final_status.setText("");
                }
            }}


        }
        else if(id==R.id.button_reset)
        {
            weight.setText("");
            alcohol_percentage.setProgress(5);
            alcohol_value.setText("");
            BAC=0;
            add_drink.setEnabled(true);
            save.setEnabled(true);
            RadioButton rd=(RadioButton)findViewById(R.id.radioButton_1oz);
            rd.setChecked(true);
            final_status.setText("");
            final_status.setBackgroundColor(Color.WHITE);
        }
        else
        {}


    }


}
