package com.sesimagotag.training.demo.services;

import com.sesimagotag.training.demo.entities.Item;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ItemServiceImpl extends ItemServices {
    private final Map<String, Item> itemsMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public String addItems(List<Item> newItems) {
        log.info("AddItems - Received AddItems call");
        int existingItemCount = 0;
        int newItemCount = 0;

        for (Item newItem : newItems) {
            String itemId = newItem.getId();
            log.info("AddItems - Received AddItems call for itemId {}", itemId);

            if (itemsMap.containsKey(itemId)) {
                existingItemCount++;
            } else {
                newItemCount++;
            }

            itemsMap.put(itemId, newItem);
        }

        String result = "First call "+ newItemCount + ", same one " + existingItemCount;
        return result;
    }

    @Override
    public Item getItem(String itemId) {
        log.info("getItem - Received getItem call for itemId: {}", itemId);
        Item item = new Item(itemsMap.get(itemId));
        return item;
    }

}
