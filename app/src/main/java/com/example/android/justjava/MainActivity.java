/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void increment(View View){
        if(i<100) {
            i++;
            display(i);
        }
        else if(i>=100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            display(100);
        }
    }
    public void decrement(View View){
        if(i>0){
            i--;
            display(i);
        }
        else{
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            display(0);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkbox= (CheckBox) findViewById(R.id.check_box);
        boolean flag=checkbox.isChecked();
        CheckBox checkbox2= (CheckBox) findViewById(R.id.check_box2);
        boolean flag2=checkbox2.isChecked();
        EditText edittext=(EditText) findViewById(R.id.edit_text);
        String name=edittext.getText().toString();

        int price=calculateprice(i,flag,flag2);
            String mesg=createOrderSummary(price,flag,flag2,name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for : "+name);
        intent.putExtra(Intent.EXTRA_TEXT, mesg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(mesg);
    }
    private int calculateprice(int i,boolean flag,boolean flag2){
        int p;
        if(flag==true && flag2==true)
            p=i*(3+5);
        else if(flag==true && flag2==false)
            p=i*6;
        else if(flag==false && flag2==false)
            p=i*5;
        else
            p=7*i;

        return p;
    }


    public String createOrderSummary(int price,boolean flag,boolean flag2,String name){


        String msg="Name:-"+name;
        msg=msg+"\nAdd whipped cream? "+flag;
        msg=msg+"\nAdd Chocolate? "+flag2;
        msg=msg+"\nQuantity:-"+i;
        msg=msg+"\nTotal:-$"+price;
        msg=msg+"\nThank You!!";
        return msg;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView= (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}