package com.example.dell.expensemanager;

public class FirebaseData {
    String amount, category, detail, payment_method;

    public FirebaseData() {
    }

    public FirebaseData(String amount, String category, String detail, String payment_method) {
        this.amount = amount;
        this.category = category;
        this.detail = detail;
        this.payment_method = payment_method;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
