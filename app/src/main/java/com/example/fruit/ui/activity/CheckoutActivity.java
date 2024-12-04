package com.example.fruit.ui.activity;
import com.example.fruit.R;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fruit.util.CouponManager;

public class CheckoutActivity extends AppCompatActivity {

    private EditText couponCodeEditText;
    private TextView finalPriceTextView;
    private Button applyCouponButton;
    private double originalPrice = 100.00; // Assuming the original price is 100
    private CouponManager couponManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        couponCodeEditText = findViewById(R.id.coupon_code_edit_text);
        finalPriceTextView = findViewById(R.id.final_price_text_view);
        applyCouponButton = findViewById(R.id.apply_coupon_button);

        couponManager = new CouponManager();

        updateFinalPrice(originalPrice);

        applyCouponButton.setOnClickListener(v -> {
            String couponCode = couponCodeEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(couponCode)) {
                double discountedPrice = couponManager.applyCoupon(couponCode, originalPrice);
                updateFinalPrice(discountedPrice);
                Toast.makeText(this, "Coupon Applied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a valid coupon code", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFinalPrice(double price) {
        finalPriceTextView.setText("Final Price: ¥" + price);
    }
}
