package de.hhu.propra.sharingplatform.service;

import de.hhu.propra.sharingplatform.model.Item;
import de.hhu.propra.sharingplatform.model.User;
import de.hhu.propra.sharingplatform.dao.ItemRepo;
import de.hhu.propra.sharingplatform.dao.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;

    public ItemService(ItemRepo itemRepo, UserRepo userRepo) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
    }

    public void persistItem(Item item, long userId) {
        if (validateItem(item)) {
            User owner = userRepo.findOneById(userId);
            item.setOwner(owner);
            itemRepo.save(item);
        }
    }

    public void removeItem(long itemId, long userId) {
        Item item = itemRepo.findOneById(itemId);
        if (userIsOwner(item, userId)) {
            item.setDeleted(true);
            itemRepo.save(item);
        }
    }

    public Item getItem(long itemId, long userId) {
        Item item = itemRepo.findOneById(itemId);
        if (userIsOwner(item, userId)) {
            return item;
        }
        return null;
    }

    public Item findItem(long itemId) {
        return itemRepo.findOneById(itemId);
    }

    public void editItem(Item newItem, long oldItemId, long userId) {
        if (validateItem(newItem) && userIsOwner(itemRepo.findOneById(oldItemId), userId)) {
            Item oldItem = itemRepo.findOneById(oldItemId);
            newItem.setOwner(oldItem.getOwner());
            newItem.setId(oldItem.getId());
            itemRepo.save(newItem);
        }
    }

    private boolean userIsOwner(Item item, long userId) {
        return item.getOwner().getId() == userId;
    }

    private boolean validateItem(Item item) {
        return (item.getDescription() != null && item.getDeposit() != null
            && item.getLocation() != null && item.getName() != null && item.getPrice() != null);
    }

    public Item forceGetItem(long itemId) {
        return itemRepo.findOneById(itemId);
    }
}
