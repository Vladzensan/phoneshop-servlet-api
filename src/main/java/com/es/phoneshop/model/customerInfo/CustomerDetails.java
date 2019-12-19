package com.es.phoneshop.model.customerInfo;

public class CustomerDetails {
    private String name;
    private String surname;
    private String deliveryDate;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;

    public CustomerDetails(String name, String surname, String deliveryDate, String deliveryAddress, PaymentMethod paymentMethod) {
        this.name = name;
        this.surname = surname;
        this.deliveryDate = deliveryDate;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public enum PaymentMethod {
        Cash, CreditCard
    }


}
