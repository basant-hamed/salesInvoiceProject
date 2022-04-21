/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sip.model;

/**
 * @author BosBos
 */
public class invoiceLine {
    private String item;
    private double price;
    private int count;
private invoiceHeader header;

    public invoiceLine() {
    }

    public invoiceLine(String item, double price, int count, invoiceHeader header) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public invoiceHeader getHeader() {
        return header;
    }

    public void setHeader(invoiceHeader header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public double getLineTotal(){
    return price * count;
    }

    @Override
    public String toString() {
        //return "invoiceLine{" + "item=" + item + ", price=" + price + ", count=" + count + ", header=" + header + '}';
    return header.getNum() + "," + item + "," + price + "," + count;
    }
    
}
