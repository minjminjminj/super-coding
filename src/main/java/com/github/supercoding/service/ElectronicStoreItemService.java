package com.github.supercoding.service;

import com.github.supercoding.repository.ElectronicStoreItemRepository;
import com.github.supercoding.repository.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {
    private ElectronicStoreItemRepository electronicStoreItemRepository;

    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
    }

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream()
                .map(Item::new)
                .collect(Collectors.toList());
        return items;
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity = new ItemEntity(null, itemBody.getName(), itemBody.getType(),
                itemBody.getPrice(), itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());
        return electronicStoreItemRepository.saveItem(itemEntity);
    }

    public Item findItemById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(idInt);
        Item item = new Item(itemEntity);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream()
                .map(Item::new)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
        return items;
    }

    public void deleteItem(String id) {
        Integer idInt = Integer.valueOf(id);
        electronicStoreItemRepository.deleteItem(idInt);
    }

    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(idInt, itemBody.getName(), itemBody.getType(), itemBody.getPrice(),
                itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());

        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(idInt, itemEntity);

        Item itemUpdated = new Item(itemEntityUpdated);

        return itemUpdated;
    }
}
