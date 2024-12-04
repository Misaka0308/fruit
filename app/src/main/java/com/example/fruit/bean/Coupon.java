package com.example.fruit.bean;

public class Coupon {
    private String code;       // 优惠券代码
    private double discount;   // 折扣金额（例如：10表示10元，0.2表示20%）
    private boolean isUsed;    // 是否已使用

    // Constructor
    public Coupon(String code, double discount) {
        this.code = code;
        this.discount = discount;
        this.isUsed = false;
    }

    // Getter and Setter methods
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    // 判断优惠券是否有效
    public boolean isValid() {
        return !isUsed;
    }
}
