package com.mobiquityinc.lib.model;

import java.util.ArrayList;
import java.util.List;

public class Package {

    public final int maxWeight;
    public final List<Item> items;

    public Package(int maxWeight, List<Item> items) {
        this.maxWeight = maxWeight;
        this.items = new ArrayList<>(items);
    }
}
