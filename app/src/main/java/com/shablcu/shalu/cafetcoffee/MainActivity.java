package com.shablcu.shalu.cafetcoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "you cannot order coffees more than 100", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        EditText text = (EditText) findViewById(R.id.edtext_name);
        String name = text.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        CheckBox chocolateCreamCheckbox = (CheckBox) findViewById(R.id.choclate_cream_checkbox);
        boolean hasChocolateCream = chocolateCreamCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolateCream);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolateCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_SUBJECT, "just coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        // displayMessage(priceMessage);
    }


    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "you cannot order the less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    private int calculatePrice(boolean addWhippedCream, boolean addChocolateCream) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolateCream) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;

    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolateCream) {
        String priceMessage = "\nname:" + name;
        priceMessage += "\n add whipped cream? " + addWhippedCream;
        priceMessage += "\n add whipped cream? " + addChocolateCream;
        priceMessage = priceMessage + "\nquantity=" + quantity;
        priceMessage += "\n total price=" + price;
        priceMessage = priceMessage + "\n thank you";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);


        quantityTextView.setText("" + number);
    }



    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);

        NumberFormat num = NumberFormat.getCurrencyInstance();
        String a = num.format(number);

        //priceTextView.setText(a);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

    // private void displayMessage(String message) {
    //   TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
    // orderSummaryTextView.setText(message);
    //  }


}


