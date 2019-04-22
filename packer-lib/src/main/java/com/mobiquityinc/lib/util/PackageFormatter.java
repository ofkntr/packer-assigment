package com.mobiquityinc.lib.util;

import com.mobiquityinc.lib.model.Item;
import com.mobiquityinc.lib.model.Package;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@NoArgsConstructor
public class PackageFormatter {

    public static final String EMPTY_ITEMS_MARK = "-";

    public static final String ITEM_SEPARATOR = ",";

    public String format(List<Package> packages) {
        return packages.stream().map(pkg -> pkg.items).map(this::toItemsString).collect(joining(lineSeparator()));
    }

    private String toItemsString(List<Item> items) {
        if (items.isEmpty()) {
            return EMPTY_ITEMS_MARK;
        }
        return items.stream().map(item -> item.index).map(Objects::toString).collect(joining(ITEM_SEPARATOR));
        //return items.stream().map(item -> item.toString()).collect(joining(ITEM_SEPARATOR));
    }
}
