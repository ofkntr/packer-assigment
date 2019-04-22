package com.mobiquityinc.lib.service;


import com.mobiquityinc.lib.model.Item;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

/**
 * Dynamic programming algorithm
 * https://en.wikipedia.org/wiki/Knapsack_problem#0.2F1_knapsack_problem
 */
@Slf4j
@Component
@NoArgsConstructor
public class KnapsackService {

    private double[][] table;

    private List<Item> items;

    private int maxWeight;

    public void createTable(List<Item> items, int maxWeight) {
        this.items = items;
        this.maxWeight = maxWeight;
        double[][] computationsStorage = new double[maxWeight + 1][items.size()];
        for (int j = 0; j < maxWeight + 1; j++) {
            for (int i = 0; i < items.size(); i++) {
                computationsStorage[j][i] = -1;
            }
        }
        table = computationsStorage;
    }

    public List<Item> filter() {
        fillTable(maxWeight, items.size() - 1);
        return getFilteredItems();
    }

    private double fillTable(int j, int i) {
        if (i < 0 || j < 0) {
            return 0;
        }
        Item item = items.get(i);
        double with, without, cell = table[j][i];
        if (cell == -1) {
            if (item.weight > j) {
                with = -1;
            } else {
                with = item.price + fillTable(j - (int) item.weight, i - 1);
            }
            without = fillTable(j, i - 1);
            cell = max(with, without);
            table[j][i] = cell;
        }
        return cell;
    }

    private List<Item> getFilteredItems() {
        List<Item> filteredItems = new ArrayList<>();
        int i = items.size() - 1;
        int j = maxWeight;
        while (i >= 0) {
            Item item = items.get(i);
            double without = i == 0 ? 0 : table[j][i - 1];
            if (!equals(table[j][i], without)) {
                filteredItems.add(item);
                j -= (int) item.weight;
            }
            i--;
        }
        return filteredItems;
    }

    private boolean equals(double d1, double d2) {
        return Double.compare(d1, d2) == 0;
    }
}
