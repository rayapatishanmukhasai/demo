package com.sesimagotag.training.demo.services;

import com.sesimagotag.training.demo.entities.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

import static com.sesimagotag.training.demo.utils.CommonUtils.reverseString;

@Slf4j
public class ItemServiceImpl extends ItemServices {
    private final Map<String, Item> itemsMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public String addItems(List<Item> newItems) {
        log.info("AddItems - Received AddItems call");
        int existingItemCount = 0;
        int newItemCount = 0;

        try {
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

            String result = "First call " + newItemCount + ", same one " + existingItemCount;
            return result;
        } catch (Exception ex) {
            log.error("Error occurred while adding items: " + ex.getMessage(), ex);
            throw new ItemServiceException("Error occurred while adding items.", ex);
        }
    }

    @Override
    public Item getItem(String itemId) {
        log.info("getItem - Received getItem call for itemId: {}", itemId);

        try {
            Item item = new Item(itemsMap.get(itemId));
            return item;
        } catch (Exception ex) {
            log.error("Error occurred while getting item: " + ex.getMessage(), ex);
            throw new ItemNotFoundException("Item not found with ID: " + itemId, ex);
        }
    }

    @Override
    public Item getItemWithReverseName(String itemId) {
        log.info("getItemWithReverseName - Received getItem with reverse name call for itemId: {}", itemId);

        try {
            Item item = new Item(itemsMap.get(itemId));
            item.setName(reverseString(item.getName()));
            return item;
        } catch (Exception ex) {
            log.error("Error occurred while getting item with reverse name: " + ex.getMessage(), ex);
            throw new ItemNotFoundException("Item not found with ID: " + itemId, ex);
        }
    }

    @Override
    public ArrayList<Item> getItems() {
        log.info("getItems - Received getItems call");

        try {
            ArrayList<Item> items = new ArrayList<>(itemsMap.values());
            return items;
        } catch (Exception ex) {
            log.error("Error occurred while getting items: " + ex.getMessage(), ex);
            throw new ItemServiceException("Error occurred while getting items.", ex);
        }
    }

    @Override
    public ArrayList<Item> getItemsWithReversedName(ArrayList<Item> items) {
        log.info("getItemsWithReverseName - Received getItems with reverse name call");

        try {
            ArrayList<Item> itemsWithReversedName = new ArrayList<>();

            for (Item item : items) {
                log.info("getItemWithReverseName - Received reverse name call for itemId: {}", item.getId());
                Item tempItem = new Item(item);
                tempItem.setName(reverseString(tempItem.getName()));
                itemsWithReversedName.add(tempItem);
            }

            return itemsWithReversedName;
        } catch (Exception ex) {
            log.error("Error occurred while getting items with reversed name: " + ex.getMessage(), ex);
            throw new ItemServiceException("Error occurred while getting items with reversed name.", ex);
        }
    }

    @Override
    public ArrayList<Item> getItemsInSortedOrder() {
        log.info("getItemsInSortedOrder - Received getItems in sorted order call");

        try {
            ArrayList<Item> items = getItems();
            Collections.sort(items, new ItemSortingComparator());
            return items;
        } catch (Exception ex) {
            log.error("Error occurred while getting items in sorted order: " + ex.getMessage(), ex);
            throw new ItemServiceException("Error occurred while getting items in sorted order.", ex);
        }
    }

    @Override
    public ArrayList<Item> getItemsByPagination(int page, int pageSize, boolean sort, boolean reverseName) {
        try {
            log.info("getItemsByPagination - Received getItems by pagination call");

            ArrayList<Item> items;
            ArrayList<Item> finalItemsResult = new ArrayList<>();

            if (sort) {
                items = getItemsInSortedOrder();

            } else {
                items = getItems();
            }

            if (reverseName) {
                items = getItemsWithReversedName(items);
            }

            int startingItemPosition = (page - 1) * pageSize;
            int endingItemPosition = page * pageSize;

            int itemsSize = items.size();
            for (int i = startingItemPosition; i < endingItemPosition; i++) {
                if (i < itemsSize && i >= 0) {
                    finalItemsResult.add(items.get(i));
                } else{
                    break;
                }
            }

            return finalItemsResult;

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    // Custom Exception for ItemService
    public static class ItemServiceException extends RuntimeException {
        public ItemServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Custom Exception for Item Not Found
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
