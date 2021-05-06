package sg.edu.rp.c346.id20007998.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    EditText AmtInput;
    EditText PaxInput;
    ToggleButton btnSVS;
    ToggleButton btnGST;
    EditText DiscountInput;
    RadioGroup PaymentMethod;
    Button btnSplit;
    Button btnReset;
    TextView tvTotal;
    TextView tvEach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AmtInput=findViewById(R.id.E_amt);
        PaxInput=findViewById(R.id.E_pax);
        btnSVS=findViewById(R.id.Tg_SVS);
        btnGST=findViewById(R.id.Tg_GST);
        DiscountInput=findViewById(R.id.E_discount);
        PaymentMethod=findViewById(R.id.RadioGroupPayMethod);
        btnSplit=findViewById(R.id.buttonSplit);
        btnReset=findViewById(R.id.buttonReset);
        tvTotal=findViewById(R.id.T_total_bill);
        tvEach=findViewById(R.id.T_each_pay);

        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AmtInput.getText().toString().trim().length()!=0 && PaxInput.getText().toString().trim().length()!=0){

                    Double totalAmt=Double.parseDouble(AmtInput.getText().toString());
                    Double newAmt=0.0;
                    //SVS & GST
                    if(btnSVS.isChecked()==false && btnGST.isChecked()==false){
                        newAmt=totalAmt;
                    }
                    else if(btnSVS.isChecked()==false && btnGST.isChecked()==true){
                        newAmt=totalAmt*1.07;
                    }
                    else if(btnSVS.isChecked()==true && btnGST.isChecked()==false){
                        newAmt=totalAmt*1.1;
                    }
                    else if(btnSVS.isChecked()==true && btnGST.isChecked()==true){
                        newAmt=totalAmt*1.17;
                    }

                    //Discount
                    if(DiscountInput.getText().toString().trim().length()!=0){
                        double discountRate=Double.parseDouble(DiscountInput.getText().toString())/100;
                        newAmt=newAmt*discountRate;
                    }

                    //pax
                    Double eachPay=0.0;
                    if(Integer.parseInt(PaxInput.getText().toString())>0){
                        eachPay=newAmt/Integer.parseInt(PaxInput.getText().toString());
                    }

                    //mode of payment

                    String DisplayMsg="";

                    if(PaymentMethod.getCheckedRadioButtonId()==R.id.radioButtonPayNow){
                        DisplayMsg=" via PayNow to 912345678";
                    }
                    else if (PaymentMethod.getCheckedRadioButtonId()==R.id.radioButtonCash){
                        DisplayMsg=" in cash";
                    }
                    tvTotal.setText("Total Bill:$ "+ String.format("%.2f",newAmt));
                    tvEach.setText("Each Pays: $"+String.format("%.2f %s",eachPay,DisplayMsg));
                }
                else if(AmtInput.getText().toString().isEmpty() || PaxInput.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please fill up all infromation",Toast.LENGTH_LONG).show();
                }


            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AmtInput.setText("");
                PaxInput.setText("");
                DiscountInput.setText("");
                btnGST.setChecked(false);
                btnSVS.setChecked(false);
                PaymentMethod.check(R.id.radioButtonCash);
            }


        });

    }
}