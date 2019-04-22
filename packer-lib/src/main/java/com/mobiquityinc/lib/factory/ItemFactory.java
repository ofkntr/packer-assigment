package com.mobiquityinc.lib.factory;

import com.mobiquityinc.lib.exception.APIException;
import com.mobiquityinc.lib.model.Item;
import com.mobiquityinc.lib.service.KnapsackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.compare;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFactory {

    private final KnapsackService knapsackService;

    static final double MAX_ITEM_WEIGHT = 100.0;

    static final int MAX_ITEM_PRICE = 100;

    static final int MAX_ITEM_SIZE = 15;

    private static final Pattern ITEM_PATTERN = Pattern.compile("\\((\\d+),(\\d+\\.\\d+),€(\\d+)\\)");

    private static final Pattern ITEMS_PATTERN = Pattern.compile(" ");

    /**
     * Create Item according to items regex pattern
     * @param maxWeight
     * @param definition
     * @return
     */
    public List<Item> create(int maxWeight, String definition) {
        if (definition.isEmpty()) {
            return emptyList();
        }
        List<Item> items = ITEMS_PATTERN.splitAsStream(definition)
                .map(this::toItem)
                .sorted((i1, i2) -> compare(i1.weight, i2.weight))
                .collect(toList());
        validateItemsSize(items);
        return filterByMaxWeight(items, maxWeight);
    }

    /**
     * Convert to item according to item regex pattern
     * @param definition
     * @return
     */
    private Item toItem(String definition) {
        Matcher matcher = ITEM_PATTERN.matcher(definition);
        if (!matcher.find()) {
            throw new APIException("String should contains item definition in format (1,2.0,€3).");
        }
        int index = parseInt(matcher.group(1));
        double weight = parseDouble(matcher.group(2));
        validateWeight(weight);
        int price = parseInt(matcher.group(3));
        validatePrice(price);
        return new Item(index, weight, price);
    }

    /**
     * Validate weight according to given constraints
     * @param weight
     */
    private void validateWeight(double weight) {
        if (weight > MAX_ITEM_WEIGHT) {
            throw new APIException("Item weight should be less or equals to " + MAX_ITEM_WEIGHT + ".");
        }
    }

    /**
     * Validate price according to given constraints
     * @param price
     */
    private void validatePrice(int price) {
        if (price > MAX_ITEM_PRICE) {
            throw new APIException("Item price should be less or equals to " + MAX_ITEM_PRICE + ".");
        }
    }

    /**
     * Validate item size according to given constraints
     * @param items
     */
    private void validateItemsSize(List<Item> items) {
        if (items.size() > MAX_ITEM_SIZE) {
            throw new APIException("Package should contains not more than " + MAX_ITEM_SIZE + " filteredItems.");
        }
    }

    /**
     * Filter max weight used with knapsack algorithm
     * @param items
     * @param maxWeight
     * @return
     */
    private List<Item> filterByMaxWeight(List<Item> items, int maxWeight) {
        knapsackService.createTable(items, maxWeight);
        return knapsackService.filter().stream()
                .sorted((i1, i2) -> compare(i1.index, i2.index))
                .collect(toList());
    }
}
