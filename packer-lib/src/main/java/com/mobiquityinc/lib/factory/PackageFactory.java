package com.mobiquityinc.lib.factory;

import com.mobiquityinc.lib.exception.APIException;
import com.mobiquityinc.lib.model.Item;
import com.mobiquityinc.lib.model.Package;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class PackageFactory {

    private static final int MAX_PACKAGE_WEIGHT = 100;

    private final ItemFactory itemsFactory;

    private static final Pattern MULTILINE_PATTERN = Pattern.compile("\\r?\\n");

    private static final Pattern WEIGHT_PATTERN = Pattern.compile("(\\d+) : (.*)");

    /**
     * Create package according to given item
     * @param definitions
     * @return
     */
    public List<Package> create(String definitions) {
        if (definitions.isEmpty()) {
            throw new APIException("String should contains package weight.");
        }
        List<Package> packages = MULTILINE_PATTERN.splitAsStream(definitions)
                .map(this::toPackage)
                .collect(toList());
        return filterByValuableWeight(packages);
    }

    /**
     * Convert package according to given item
     * @param definition
     * @return
     */
    private Package toPackage(String definition) {
        Matcher matcher = WEIGHT_PATTERN.matcher(definition);
        if (!matcher.find()) {
            throw new APIException("String should contains package weight.");
        }
        int weight = parseInt(matcher.group(1));
        validateWeight(weight);
        List<Item> items = itemsFactory.create(weight, matcher.group(2));
        return new Package(weight, items);
    }

    /**
     * Validate weight according to given constraints
     * @param weight
     */
    private void validateWeight(int weight) {
        if (weight > MAX_PACKAGE_WEIGHT) {
            throw new APIException("Package weight should be less or equals to " + MAX_PACKAGE_WEIGHT + ".");
        }
    }

    /**
     * Filter package acoorging to totatl price and total weight
     * @param packages
     * @return
     */
    private List<Package> filterByValuableWeight(List<Package> packages) {
        packages.removeIf(pkg1 -> packages.stream()
                .anyMatch(pkg2 -> pkg1 != pkg2
                        && getTotalPrice(pkg1) == getTotalPrice(pkg2)
                        && getTotalWeight(pkg1) >= getTotalWeight(pkg2))
        );
        return packages;
    }

    /**
     * Get total weight to given package
     * @param pkg
     * @return
     */
    private double getTotalWeight(Package pkg) {
        return pkg.items.stream().mapToDouble(item -> item.weight).sum();
    }

    /**
     * Get total price to given package
     * @param pkg
     * @return
     */
    private int getTotalPrice(Package pkg) {
        return pkg.items.stream().mapToInt(item -> item.price).sum();
    }
}
