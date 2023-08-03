package com.sesimagotag.training.demo.services;

import com.sesimagotag.training.demo.entities.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemServices {

    public abstract String addItems(List<Item> newItems);

    public abstract Item getItem(String itemId);

    public abstract Item getItemWithReverseName(String itemId);

    public abstract ArrayList<Item> getItems();

    public abstract ArrayList<Item> getItemsWithReversedName(ArrayList<Item> items);

    public abstract ArrayList<Item> getItemsInSortedOrder();
}
