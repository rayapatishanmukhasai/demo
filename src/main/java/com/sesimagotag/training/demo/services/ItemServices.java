package com.sesimagotag.training.demo.services;

import com.sesimagotag.training.demo.entities.Item;

import java.util.List;

public abstract class ItemServices {

    public abstract String addItems(List<Item> newItems);

    public abstract Item getItem(String itemId);
}
