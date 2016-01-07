package fr.insa.h4401.gfaim;

public class Allergie {

    private boolean selected;
    private String name;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Allergie(boolean selected, String name) {
        this.selected = selected;
        this.name = name;
    }
}
