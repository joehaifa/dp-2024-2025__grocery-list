package com.fges.webPage;

import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyGroceryShopAdapterTest {

    @Test
    void convertsDomainObjectsToWebItems_withoutReferencingTheirType() throws Exception {
        //Arrange – mock DAO providing a single GroceryItem
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.getItems()).thenReturn(
                Arrays.asList(new GroceryItem("banane", 3, "fruit"))
        );

        // Act – adapt to web representation
        MyGroceryShopAdapter adapter = new MyGroceryShopAdapter(dao);
        List<?> groceries = adapter.getGroceries();

        // Assert – size then reflective field checks
        assertEquals(1, groceries.size());

        Object first = groceries.get(0);
        Method nameMethod     = first.getClass().getMethod("name");
        Method quantityMethod = first.getClass().getMethod("quantity");
        Method categoryMethod = first.getClass().getMethod("category");

        assertEquals("banane", nameMethod.invoke(first));
        assertEquals(3,        quantityMethod.invoke(first));
        assertEquals("fruit",  categoryMethod.invoke(first));
    }
}
