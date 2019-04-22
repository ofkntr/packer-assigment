package com.mobiquityinc.lib.config;

import com.mobiquityinc.lib.factory.ItemFactory;
import com.mobiquityinc.lib.factory.PackageFactory;
import com.mobiquityinc.lib.packer.Packer;
import com.mobiquityinc.lib.service.KnapsackService;
import com.mobiquityinc.lib.util.PackageFormatter;
import com.mobiquityinc.lib.util.PackageReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PackerSupportConfiguration {

    @Bean
    KnapsackService knapsackService() {
        return new KnapsackService();
    }

    @Bean
    ItemFactory itemFactory(KnapsackService knapsackService) {
        return new ItemFactory(knapsackService);
    }

    @Bean
    PackageFactory packageFactory(ItemFactory itemFactory) {
        return new PackageFactory(itemFactory);
    }

    @Bean
    PackageFormatter packageFormatter() {
        return new PackageFormatter();
    }

    @Bean
    PackageReader packageReader()  {
        return new PackageReader();
    }

    @Bean
    Packer packer(PackageFactory packageFactory, PackageFormatter packageFormatter, PackageReader packageReader) {
        return new Packer(packageFactory, packageFormatter, packageReader);
    }

}
