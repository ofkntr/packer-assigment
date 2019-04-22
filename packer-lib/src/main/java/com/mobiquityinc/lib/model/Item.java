package com.mobiquityinc.lib.model;

public class Item {

    public final int index;
    public final double weight;
    public final int price;

    public Item(int index, double weight, int price) {
        this.index = index;
        this.weight = weight;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
