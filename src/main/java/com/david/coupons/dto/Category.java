package com.david.coupons.dto;

public class Category {

    private int id;
    private String name;

    public Category() {

    }
    public Category(int id, String name) {
        this(name);
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
