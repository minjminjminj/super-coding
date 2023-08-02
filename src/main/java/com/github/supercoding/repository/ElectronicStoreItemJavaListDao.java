package com.github.supercoding.repository;

import com.github.supercoding.web.dto.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ElectronicStoreItemJavaListDao implements ElectronicStoreItemRepository {
    private static int serialItemId = 1;
    private List<Item> items = new ArrayList<>(Arrays.asList(
            new Item(String.valueOf(serialItemId++), "Apple iPhone 12 Pro Max", "Smartphone", 1490000, "A14 Bionic", "512GB"),
            new Item(String.valueOf(serialItemId++), "Samsung Galaxy S21 Ultra", "Smartphone", 1690000, "Exynos 2100", "256GB"),
            new Item(String.valueOf(serialItemId++), "Google Pixel 6 Pro", "Smartphone", 1290000, "Google Tensor", "128GB"),
            new Item(String.valueOf(serialItemId++), "Dell XPS 15", "Laptop", 2290000, "Intel Core i9", "1TB SSD"),
            new Item(String.valueOf(serialItemId++), "Sony Alpha 7 III", "Mirrorless Camera", 2590000, "BIONZ X", "No internal storage"),
            new Item(String.valueOf(serialItemId++), "Microsoft Xbox Series X", "Gaming Console", 499000, "Custom AMD Zen 2", "1TB SSD")
    ));

    @Override
    public List<ItemEntity> findAllItems() {
        return null;
    }

    @Override
    public Integer saveItem(ItemEntity itemEntity) {
        return null;
    }

    @Override
    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
        return null;
    }

    @Override
    public void deleteItem(int parseInt) {

    }

    @Override
    public ItemEntity findItemById(Integer idInt) {
        return null;
    }
}
