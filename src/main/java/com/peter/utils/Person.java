package com.peter.utils;

/**
 * @author lcc
 * @date 2020/9/20 1:12
 */
public class Person {
    private String name;
    private int age;
    private Person spouse;
    private Person[] children;
    public Person(){
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }
    public void setChildren(Person[] children) {
        this.children = children;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    public Person getSpouse() {
        return this.spouse;
    }
    public Person[] getChildren() {
        return this.children;
    }
}
