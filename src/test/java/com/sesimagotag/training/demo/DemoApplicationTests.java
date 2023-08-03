package com.sesimagotag.training.demo;

import com.sesimagotag.training.demo.entities.Item;
import com.sesimagotag.training.demo.services.ItemServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;


@SpringBootTest
class DemoApplicationTests {

	private ItemServiceImpl itemService;

	@Before
	public void setUp() {
		itemService = new ItemServiceImpl();
	}

	@Test
	public void testAddItems() {
		List<Item> newItems = new ArrayList<>();
		newItems.add(new Item("aaa1", new BigDecimal(4.54), "5234"));
		newItems.add(new Item("aaa2", new BigDecimal(2.54), "3235"));

		String result = itemService.addItems(newItems);
		Assertions.assertEquals("First call 2, same one 0", result);

		// Add duplicate items
		newItems.add(new Item("aaa1", new BigDecimal(4.54), "5234"));
		result = itemService.addItems(newItems);
		Assertions.assertEquals("First call 0, same one 2", result);
	}

	@Test
	public void testGetItem() {
		Item item1 = new Item("aaa1", new BigDecimal(4.54), "5234");
		itemService.addItems(List.of(item1));

		// Test existing item
		Item resultItem = itemService.getItem("aaa1");
		Assertions.assertEquals(item1, resultItem);

		// Test non-existing item
		resultItem = itemService.getItem("nonexistent");
		Assertions.assertNull(resultItem);
	}

}
