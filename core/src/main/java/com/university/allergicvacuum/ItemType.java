package com.university.allergicvacuum;

/** Every kind of item that can be found in a room, with its image. */
public enum ItemType {

    PAPER("sprites/paper.png"),
    CLOTH("sprites/cloth.png"),
    BAG("sprites/bag.png"),
    BOOK("sprites/book.png"),
    PLASTIC_BAG("sprites/plasticbag.png"),
    CAN("sprites/can.png"),
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
