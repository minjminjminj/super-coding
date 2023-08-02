package com.github.supercoding.web.controller;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {
    private ElectronicStoreItemService electronicStoreItemService;

    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
        this.electronicStoreItemService = electronicStoreItemService;
    }

    // 1. 모든 아이템 조회 (GET)
    @GetMapping("/items")
    public List<Item> getAllItems() {
        return electronicStoreItemService.findAllItems();
    }

    // 2. 새로운 아이템 등록 (POST)
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: "  + itemId;
    }

    // 3. ID Path로 아이템 조회 (GET)
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable String id) {
        return electronicStoreItemService.findItemById(id);
    }

    // 4. Query parameter로 ID 조회 (GET)
    @GetMapping("/items-query")
    public Item getItemByQueryId(@RequestParam("id") String id) {
        return electronicStoreItemService.findItemById(id);
    }

    // 5. 여러 ID Query parameter로 조회 (GET)
    @GetMapping("/items-queries")
    public List<Item> getItemByQueryId(@RequestParam("id") List<String> ids) {
        return electronicStoreItemService.findItemsByIds(ids);
    }

    // 6. Path ID로 아이템 삭제 (DELETE)
    @DeleteMapping("/items/{id}")
    public String deleteItemById(@PathVariable String id) {
        electronicStoreItemService.deleteItem(id);
        return "Object with id =" + id + "has been deleted";
    }

    // 7. Path ID와 Body로 업데이트 (UPDATE)
    @PutMapping("items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody) {
        return electronicStoreItemService.updateItem(id, itemBody);
    }
}
