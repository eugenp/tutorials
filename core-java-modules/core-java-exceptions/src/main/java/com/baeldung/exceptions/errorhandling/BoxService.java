package com.baeldung.exceptions.errorhandling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BoxService {
    private final static Logger logger = Logger.getLogger("BoxServiceLogging");
    private final boolean hasBoxes = false;

    public void addToyInBox(Box box, Toy toy) {
        BoxResponseCode boxResponseCode = box.open();

        if (boxResponseCode != BoxResponseCode.BOX_NOT_FOUND_ERROR) {
            boxResponseCode = box.addToy(toy);

            if (boxResponseCode == BoxResponseCode.BOX_FULL_ERROR) {
                logger.warning("Box full. Cannot add the toy.");
            } else if (boxResponseCode == BoxResponseCode.SUCCESS) {
                logger.info("Toy added successfully.");
            }

            boxResponseCode = box.close();
        }
    }

    public void addToyInBoxClean(Box box, Toy toy) {
        try {
            tryToAddToyInBox(box, toy);
        } catch (BoxAddError e) {
            // error handling
        }
    }

    private void tryToAddToyInBox(Box box, Toy toy) throws BoxAddError {
        box.open();
        box.addToy(toy);
        box.close();
    }

    public void openAllBoxes() {
        List<Box> boxes = getAllBoxes();
        if (boxes != null) {
            for (Box box : boxes) {
                // ...
            }
        }
    }

    public void openAllBoxesClean() {
        List<Box> boxes = getAllBoxes();

        for (Box box : boxes) {
            // ...
        }
    }

    private List<Box> getAllBoxes() {
        if (!hasBoxes) {
            return Collections.emptyList();
        } else {
            return new ArrayList<Box>();
        }
    }

    public Optional<Box> findBoxById(int boxId) {
        boolean found = false;

        if (!found) {
            return Optional.empty();
        } else {
            return Optional.of(new Box());
        }
    }
}
