package cn.com.core.data.model;

public class Person {
    public String name;
    public String phone;
    public String idcard;
    public String sex;

    public Person() {
    }

    public Person(String name, String phone, String idcard, String sex) {
        this.name = name;
        this.phone = phone;
        this.idcard = idcard;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getSex() {
        return sex;
    }
}
