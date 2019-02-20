package de.hhu.propra.sharingplatform.service.validation;

import de.hhu.propra.sharingplatform.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static de.hhu.propra.sharingplatform.service.validation.Validator.validateName;

public class ItemValidator {
    public static void validateItem(Item item) {

        if (!Validator.matchesDbGuidelines(item.getDescription())
            || !Validator.isPrintable(item.getDescription())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Description");
        }
        if (item.getBail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Bail");
        }
        if (item.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Price");
        }
        validateName(item.getLocation(), "Invalid Location");
        validateName(item.getName(), "Invalid Item Name");
    }
}
