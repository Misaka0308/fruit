package com.example.fruit.util;

import com.example.fruit.bean.Coupon;
import java.util.ArrayList;
import java.util.List;

public class CouponManager {
    private List<Coupon> coupons;

    public CouponManager() {
        this.coupons = new ArrayList<>();
        // 创建一些示例优惠券
        coupons.add(new Coupon("DISCOUNT10", 10));  // 10元优惠券
        coupons.add(new Coupon("DISCOUNT20", 0.2)); // 20%优惠券
    }

    // 获取所有可用的优惠券
    public List<Coupon> getAvailableCoupons() {
        List<Coupon> availableCoupons = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.isValid()) {
                availableCoupons.add(coupon);
            }
        }
        return availableCoupons;
    }

    // 应用优惠券
    public double applyCoupon(String code, double originalPrice) {
        for (Coupon coupon : coupons) {
            if (coupon.getCode().equals(code) && coupon.isValid()) {
                coupon.setUsed(true); // 标记优惠券为已使用
                // 如果是百分比优惠
                if (coupon.getDiscount() < 1) {
                    return originalPrice * (1 - coupon.getDiscount());
                } else {
                    // 如果是固定金额优惠
                    return originalPrice - coupon.getDiscount();
                }
            }
        }
        // 如果优惠券无效或不存在，则返回原价
        return originalPrice;
    }
}
