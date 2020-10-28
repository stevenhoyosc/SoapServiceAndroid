package com.example.soapserviceconsume;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private EditText firstMoney, secondMoney, amount, resultV;
    private Button btnAction;
    SoapPrimitive resultString;
    String param1, param2, param3, param4, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstMoney = (EditText) findViewById(R.id.firstMoney);
        secondMoney = (EditText) findViewById(R.id.secondMoney);
        amount = (EditText) findViewById(R.id.amount);
        resultV = (EditText) findViewById(R.id.resultConversion);
        btnAction = (Button) findViewById(R.id.operation);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param1 = firstMoney.getText().toString();
                param2 = secondMoney.getText().toString();
                param3 = "2020-10-28";
                param4 = amount.getText().toString();
                AsyncrhonismCall taskS = new AsyncrhonismCall();
                taskS.execute();


            }
        });
    }

    private class AsyncrhonismCall extends  AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            convert();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            resultV.setText("Conversion "+resultString.toString());
        }
    }

    


    public void convert() {
        final String SOAP_ACTION = "http://tempuri.org/GetConversionAmount";
        final String METHOD_NAME = "GetConversionAmount";
        final String NAMESPACE = "http://tempuri.org/";
        final String URL = "http://currencyconverter.kowabunga.net/converter.asmx";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("CurrencyFrom", param1);
            request.addProperty("CurrencyTo", param2);
            request.addProperty("RateDate", param3);
            request.addProperty("Amount", param4);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();
            message = "ok";

        } catch (Exception e) {
            message = "Error" + e.getMessage();
        }

    }

}