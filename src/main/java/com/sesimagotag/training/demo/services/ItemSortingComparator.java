package com.sesimagotag.training.demo.services;

import com.sesimagotag.training.demo.entities.Item;

import java.util.Comparator;

public class ItemSortingComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {

        // Ascending order
        int priceCompare = item1.getPrice().compareTo(item2.getPrice());
        int nameCompare = item1.getName().compareTo(item2.getName());

        // Descending order
        //int priceCompare = item2.getPrice().compareTo(item1.getPrice());
        //int nameCompare = item2.getName().compareTo(item1.getName());

        return (priceCompare == 0) ? nameCompare : priceCompare;

    }
}
