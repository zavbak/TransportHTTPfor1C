package com.anit.transporthttpfor1c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *Тест 1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btExchange = (Button) findViewById(R.id.btExchange);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btExchange.setOnClickListener(this);
    }


    void executeExchange(){






    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btExchange:

                executeExchange();

                Toast.makeText(this,"Обмен выполнен",Toast.LENGTH_SHORT).show();

                break;

            default:
                break;

        }
    }






}
