package edu.icet.util;

public enum Category {
    FICTION(1, "Fiction", "F001"),
    NON_FICTION(2, "Non-Fiction", "NF001"),
    SCIENCE(3, "Science", "S001"),
    HISTORY(4, "History", "H001"),
    BIOGRAPHY(5, "Biography", "B001"),
    NOVEL(6, "Novel", "N001");

    private final int id;
    private final String name;
    private final String code;

    Category(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static Category getCategoryByCode(String code) {
        for (Category category : values()) {
            if (category.getCode().equalsIgnoreCase(code)) {
                return category;
            }
        }
        return null;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : values()) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
