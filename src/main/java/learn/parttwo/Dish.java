package learn.parttwo;

public class Dish {
    private String name;
    private boolean isVegetarian;
    private int colories;
    private DishType type;

    public Dish(String name, boolean isVegetarian, int colories, DishType type) {
        this.name = name;
        this.isVegetarian = isVegetarian;
        this.colories = colories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getColories() {
        return colories;
    }

    public DishType getType() {
        return type;
    }
}
