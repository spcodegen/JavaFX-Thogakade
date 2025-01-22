package controller.item;

import java.util.List;

public interface ItemService {
    boolean addItem();
    boolean updateItem();
    boolean deleteItem();
    List<Item> getAll();
    Item searchItem(String code);
}
