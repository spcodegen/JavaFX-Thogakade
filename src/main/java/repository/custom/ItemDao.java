package repository.custom;

import controller.item.Item;
import repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item,String> {
}
