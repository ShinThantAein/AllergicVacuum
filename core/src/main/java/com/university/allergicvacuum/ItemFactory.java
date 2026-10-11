package com.university.allergicvacuum;

/**
 * Factory pattern: creates items without the caller needing to know
 * each item's size, score or allergy.
 *
 * Every item of the same type gets the same score and allergy,
 * so players can learn which items are risky.
 */
public final class ItemFactory {

    // Width and height of the box each item is drawn in
    public static final float ITEM_SIZE = 62f;

    // Allergy added by plastic and metal items, and by everything else
    private static final int HIGH_ALLERGY = 15;
    private static final int LOW_ALLERGY = 5;

    // Only static methods, so no objects of this class are needed
    private ItemFactory() {
    }

    /** Creates a new item of the given type at (0, 0). */
    public static Item createItem(ItemType type) {
        return new Item(
            0, 0, ITEM_SIZE, ITEM_SIZE,
            type, scoreFor(type), allergyFor(type)
        );
    }

    private static int scoreFor(ItemType type) {
        switch (type) {
            case PAPER:
            case EGG:
                return 10;
            case CLOTH:
            case SWEATER:
            case BOX:
                return 15;
            case BAG:
            case OIL:
                return 20;
            case BOOK:
                return 25;
            case PLASTIC_BAG:
            case BOTTLE:
                return 30;
            case CAN:
            case PAN:
                return 35;
            case FRIDGE:
                return 40;
            default:
                // Toys
                return 30;
        }
    }

    /** The vacuum is allergic to plastic and metal. */
    private static int allergyFor(ItemType type) {
        switch (type) {
            case PLASTIC_BAG:
            case BOTTLE:
            case CAN:
            case PAN:
            case FRIDGE:
                return HIGH_ALLERGY;
            default:
                return LOW_ALLERGY;
        }
    }
}
