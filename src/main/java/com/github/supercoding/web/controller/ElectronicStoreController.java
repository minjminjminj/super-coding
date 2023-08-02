package com.github.supercoding.web.controller;

import com.github.supercoding.repository.ElectronicStoreItemRepository;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import com.github.supercoding.web.dto.Spec;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {
    private static int serialItemId = 1;
    private List<Item> items = new ArrayList<>(Arrays.asList(
            new Item(String.valueOf(serialItemId++), "Apple iPhone 12 Pro Max", "Smartphone", 1490000, "A14 Bionic", "512GB"),
            new Item(String.valueOf(serialItemId++), "Samsung Galaxy S21 Ultra", "Smartphone", 1690000, "Exynos 2100", "256GB"),
            new Item(String.valueOf(serialItemId++), "Google Pixel 6 Pro", "Smartphone", 1290000, "Google Tensor", "128GB"),
            new Item(String.valueOf(serialItemId++), "Dell XPS 15", "Laptop", 2290000, "Intel Core i9", "1TB SSD"),
            new Item(String.valueOf(serialItemId++), "Sony Alpha 7 III", "Mirrorless Camera", 2590000, "BIONZ X", "No internal storage"),
            new Item(String.valueOf(serialItemId++), "Microsoft Xbox Series X", "Gaming Console", 499000, "Custom AMD Zen 2", "1TB SSD")
    ));

    // 1. 모든 아이템 조회 (GET)
    @GetMapping("/items")
    public List<Item> findAllItems() {
        return items;
    }

    // 2. 새로운 아이템 등록 (POST)
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
        Item newItem = new Item(serialItemId++, itemBody);
        items.add(newItem);
        return "ID: " + newItem.getId();
    }

    // 3. ID Path로 아이템 조회 (GET)
    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id) {
        Item itemFounded = items.stream()
                .filter((item -> item.getId().equals(id)))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return itemFounded;
    }

    // 4. Query parameter로 ID 조회 (GET)
    @GetMapping("/items-query")
    public Item findItemByQueryId(@RequestParam("id") String id) {
        Item itemFounded = items.stream()
                                .filter((item -> item.getId().equals(id)))
                                .findFirst()
                                .orElseThrow(RuntimeException::new);

        return itemFounded;
    }

    // 5. 여러 ID Query parameter로 조회 (GET)
    @GetMapping("/items-queries")
    public List<Item> findItemsByQueryId(@RequestParam("id") List<String> ids) {
        Set<String> idSet = ids.stream().collect(Collectors.toSet());
        List<Item> itemsFounded = items.stream()
                                        .filter((item -> idSet.contains(item.getId())))
                                        .collect(Collectors.toList());

        return itemsFounded;
    }

    // 6. Path ID로 아이템 삭제 (DELETE)
    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id) {
        Item itemFounded = items.stream()
                .filter((item -> item.getId().equals(id)))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        items.remove(itemFounded);

        return "Object with id = " + itemFounded.getId() + " has been deleted";
    }

    // 7. Path ID와 Body로 업데이트 (UPDATE)
    // Java 내부의 객체를 고칠 때는 삭제 후 추가하는 업데이트 방식을 더 권장
    @PutMapping("items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody) {
        Item itemFounded = items.stream()
                .filter((item -> item.getId().equals(id)))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        items.remove(itemFounded);

        Item itemUpdated = new Item(Integer.valueOf(id), itemBody);
        items.add(itemUpdated);

        return itemUpdated;
    }
}
