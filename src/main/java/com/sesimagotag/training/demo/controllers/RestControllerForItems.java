package com.sesimagotag.training.demo.controllers;

import java.util.*;
import com.sesimagotag.training.demo.services.ItemServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sesimagotag.training.demo.entities.Item;

@RestController
public class RestControllerForItems {

    private final ItemServiceImpl itemService = new ItemServiceImpl();

    /**
     * Create all items given in parameters and overwrite existing one.
     * 
     * @return count of new items added
     */
    @PostMapping(value = "api/v1/items", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createItems(@RequestBody List<Item> newItems) {
        String result = itemService.addItems(newItems);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * @return return item with corresponding itemId
     */
    @GetMapping(value = "api/v1/items/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItem(@PathVariable String itemId) {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Do not modify existing item list on the reverse operation.
     * 
     * @return return item with corresponding itemId and reverse its name in the
     *         result.
     */
    @GetMapping(value = "/api/v1/items/{itemId}/reverse")
    public ResponseEntity<Object> getItemReverse(@PathVariable String itemId) {
        Item item = itemService.getItemWithReverseName(itemId);
        if (item == null) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Do not modify existing item list on the reverse operation.
     * 
     * @return all items with reverse name
     */
    @GetMapping(value = "api/v1/items/reverse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItemsReverse() {
        ArrayList<Item> items = itemService.getItems();
        List<Item> itemsWithReversedName = itemService.getItemsWithReversedName(items);
        return new ResponseEntity<>(itemsWithReversedName, HttpStatus.OK);
    }

    /**
     * @return all items sorted by prices asc and names asc
     */
    @GetMapping(value = "api/v1/items/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItemsSort() {
        List<Item> items = itemService.getItemsInSortedOrder();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * @return all items
     */
    @GetMapping(value = "api/v1/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItems() {
        List<Item> items = itemService.getItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * <p>
     * page=1 and pageSize=5, return 0->4 elements
     * </p>
     * <p>
     * page=2 and pageSize=5, return 5->9 elements
     * </p>
     * <p>
     * page=2 and pageSize=10, return 10->19 elements
     * </p>
     * 
     * @param page
     *                    first page, start at 1
     * @param pageSize
     *                    elements returned in a page
     * @param sort
     *                    sort by prices asc and names asc
     * @param reverseName
     *                    reverse names (after sorting)
     * @return items
     */
    @GetMapping(value = "api/v1/items/iterate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItemsIterate(@RequestParam final int page, @RequestParam final int pageSize,
                                                  @RequestParam final boolean sort, @RequestParam final boolean reverseName) {
        List<Item> items = itemService.getItemsByPagination(page, pageSize, sort, reverseName);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
