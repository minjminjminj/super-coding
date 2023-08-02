package com.github.supercoding.service;

import com.github.supercoding.repository.items.ElectronicStoreItemRepository;
import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {
    private ElectronicStoreItemRepository electronicStoreItemRepository;
    private StoreSalesRepository storeSalesRepository;

    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository, StoreSalesRepository storeSalesRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
        this.storeSalesRepository = storeSalesRepository;
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

    @Transactional
    public Integer buyItems(BuyOrder buyOrder) {
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(itemId);

        if(itemEntity.getStoreId() == null) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if(itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer successBuyItemNums;
        if(itemNums >= itemEntity.getStock()) successBuyItemNums = itemEntity.getStock();
        else successBuyItemNums = itemNums;

        Integer totalPrice = successBuyItemNums * itemEntity.getPrice();

        // Item 재고 감소
        electronicStoreItemRepository.updateItemStock(itemId, itemEntity.getStock() - successBuyItemNums);

        // Transaction 확인용 에러
        if(successBuyItemNums == 5) throw new RuntimeException("5개를 구매하는건 허락하지않습니다.");

        // 매장 매상 추가
        StoreSales storeSales = storeSalesRepository.findStoreSalesById(itemEntity.getStoreId());
        storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(), storeSales.getAmout()  + totalPrice);

        return successBuyItemNums;
    }
}
