package com.mobiquityinc.lib.util;

import com.mobiquityinc.lib.model.Item;
import com.mobiquityinc.lib.model.Package;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.mobiquityinc.lib.util.PackageFormatter.EMPTY_ITEMS_MARK;
import static com.mobiquityinc.lib.util.PackageFormatter.ITEM_SEPARATOR;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PackageFormatterTest {

    private static final Package PACKAGE_STUB = new Package(0, emptyList());

    @InjectMocks
    PackageFormatter formatter;

    @Test
    public void should_return_empty_string_for_empty_packages() {
        String result = formatter.format(emptyList());
        assertThat(result, isEmptyString());
    }

    @Test
    public void should_return_string_with_one_line_for_each_package() {
        List<Package> packages = asList(PACKAGE_STUB, PACKAGE_STUB);
        String result = formatter.format(packages);
        assertThat(countLines(result), is(2));
    }

    @Test
    public void should_return_empty_mark_if_package_counts_no_items() {
        String result = formatter.format(singletonList(PACKAGE_STUB));
        assertThat(result, is(EMPTY_ITEMS_MARK));
    }

    @Test
    public void should_return_item_index() {
        List<Item> items = singletonList(new Item(1, 0, 0));
        String result = formatter.format(singletonList(new Package(0, items)));
        assertThat(result, is("1"));
    }

    @Test
    public void should_return_items_indexes_by_specified_separator() {
        List<Item> items = asList(new Item(1, 0, 0), new Item(2, 0, 0));
        String result = formatter.format(singletonList(new Package(0, items)));
        assertThat(result, is("1" + ITEM_SEPARATOR + "2"));
    }

    private int countLines(String string) {
        return string.split(lineSeparator()).length;
    }

}