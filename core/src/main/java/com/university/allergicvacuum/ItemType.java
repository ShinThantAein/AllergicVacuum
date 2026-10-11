package com.university.allergicvacuum;

/** Every kind of item that can be found in a room, with its image. */
public enum ItemType {

    PAPER("sprites/paper.png"),
    EGG("sprites/egg.png"),
    CLOTH("sprites/cloth.png"),
    SWEATER("sprites/sweater.png"),
    BOX("sprites/box.png"),
    BAG("sprites/bag.png"),
    OIL("sprites/oil.png"),
    BOOK("sprites/book.png"),
    PLASTIC_BAG("sprites/plasticbag.png"),
    BOTTLE("sprites/bottle.png"),
    CAN("sprites/can.png"),
    PAN("sprites/pan.png"),
    FRIDGE("sprites/fridge.png"),
    TOY1("sprites/toy1.png"),
    TOY2("sprites/toy2.png"),
    TOY3("sprites/toy3.png");

    private final String textureFile;

    ItemType(String textureFile) {
        this.textureFile = textureFile;
    }

    public String getTextureFile() {
        return textureFile;
    }
}
