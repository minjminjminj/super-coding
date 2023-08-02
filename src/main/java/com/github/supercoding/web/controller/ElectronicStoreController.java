package com.github.supercoding.web.controller;

import com.github.supercoding.repository.ElectronicStoreItemRepository;
import com.github.supercoding.repository.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {
    private ElectronicStoreItemRepository electronicStoreItemRepository;

    public ElectronicStoreController(ElectronicStoreItemRepository electronicStoreItemRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
    }

    // 1. 모든 아이템 조회 (GET)
    @GetMapping("/items")
    public List<Item> getAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream()
                .map(Item::new)
                .collect(Collectors.toList());
        return items;
    }

    // 2. 새로운 아이템 등록 (POST)
    @PostMapping("/items")
    public Integer registerItem(@RequestBody ItemBody itemBody) {
        ItemEntity itemEntity = new ItemEntity(null, itemBody.getName(), itemBody.getType(),
                itemBody.getPrice(), itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());
        Integer id = electronicStoreItemRepository.saveItem(itemEntity);
        return id;
    }

    // 3. ID Path로 아이템 조회 (GET)
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(idInt);
        Item item = new Item(itemEntity);
        return item;
    }

    // 4. Query parameter로 ID 조회 (GET)
    @GetMapping("/items-query")
    public Item getItemByQueryId(@RequestParam("id") String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(idInt);
        Item item = new Item(itemEntity);
        return item;
    }

    // 5. 여러 ID Query parameter로 조회 (GET)
    @GetMapping("/items-queries")
    public List<Item> getItemByQueryId(@RequestParam("id") List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream()
                .map(Item::new)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
        return items;
    }

    // 6. Path ID로 아이템 삭제 (DELETE)
    @DeleteMapping("/items/{id}")
    public String deleteItemById(@PathVariable String id) {
        electronicStoreItemRepository.deleteItem(Integer.parseInt(id));
        return "Object with id =" + id + "has been deleted";
    }

    // 7. Path ID와 Body로 업데이트 (UPDATE)
    @PutMapping("items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody) {
        ItemEntity itemEntity = new ItemEntity(Integer.valueOf(id), itemBody.getName(), itemBody.getType(), itemBody.getPrice(),
                itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());

        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(Integer.valueOf(id), itemEntity);

        Item itemUpdated = new Item(itemEntityUpdated);

        return itemUpdated;
    }
}
