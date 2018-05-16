package com.example.s3713532.week5a1;

/**
 * Created by s3713532 on 3/26/18.
 */

public class Student {

    private String _id;
    private String name;
    private int age;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
