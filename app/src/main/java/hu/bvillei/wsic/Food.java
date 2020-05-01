package hu.bvillei.wsic;

public class Food {
    private int id;
    private String name;
    private boolean vegetarian;
    private Type type;

    public Food(int id, String name, boolean vegetarian, Type type) {
        this.id = id;
        this.name = name;
        this.vegetarian = vegetarian;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
