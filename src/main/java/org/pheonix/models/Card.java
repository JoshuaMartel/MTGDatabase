package org.pheonix.models;

import java.util.Vector;

public class Card {
    String name;
    String mana;
    String cardType;
    String subtype;
    String simpleAbilities;
    String complexAbilities;
    String cardSet;
    int power;
    int toughness;
    String colourIdentity;
    int owned;
    int available;
    Vector<String> tags;
    String loreText;
    String imageURI;

    public String getName() {
        return name;
    }
    public String getMana(){
        return mana;
    }
    public String getCardType(){
        return cardType;
    }
    public String getSubtype(){
        return subtype;
    }
    public String getSimpleAbilities(){
        return simpleAbilities;
    }
    public String getComplexAbilities(){
        return complexAbilities;
    }
    public String getCardSet(){
        return cardSet;
    }
    public int getPower(){
        return power;
    }
    public int getToughness(){
        return toughness;
    }
    public String getColourIdentity(){
        return colourIdentity;
    }
    public int getOwned(){
        return owned;
    }
    public int getAvailable(){
        return available;
    }
    public Vector<String> getTags(){
        return tags;
    }
    public String getLoreText() {
        return loreText;
    }
    public String getImageURI() {
        return imageURI;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setMana(String mana) {
        this.mana = mana;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
    public void setSimpleAbilities(String simpleAbilities) {
        this.simpleAbilities = simpleAbilities;
    }
    public void setComplexAbilities(String complexAbilities) {
        this.complexAbilities = complexAbilities;
    }
    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void setToughness(int toughness) {
        this.toughness = toughness;
    }
    public void setColourIdentity(String colourIdentity) {
        this.colourIdentity = colourIdentity;
    }
    public void setOwned(int owned) {
        this.owned = owned;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public void setTags(Vector<String> tags) {
        this.tags = tags;
    }
    public void setLoreText(String loreText) {
        this.loreText = loreText;
    }
    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

}
