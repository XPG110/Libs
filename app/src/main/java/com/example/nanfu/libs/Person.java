package com.example.nanfu.libs;

/**
 * Created by Administrator on 2016/8/25.
 */
public class Person {
    private  int age;
    private String name;
    private float salary;
    private float height;

    private Person(Builder builder){
        age=builder.age;
        name=builder.name;
        salary=builder.salary;
        height=builder.height;
    }
    public static class Builder{
        private  int age;
        private String name;
        private float salary;
        private float height;

        public Builder(){

        }

        public Builder age(int age){
            this.age=age;
            return this;
        }
        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder salary(float salary){
            this.salary=salary;
            return this;
        }
        public Builder height(float height){
            this.height=height;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

}
