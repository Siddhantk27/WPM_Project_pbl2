package application;

import java.time.LocalTime;
import java.util.Date;


public class Staff {
    private String name;
    private int eid;
    private int age;
    private String salary;
    
    private String post;
    private String contact;

    public Staff(int eid,String name, int age, String salary, String post, String contact) {
    	this.eid=eid;
        this.name = name;
        
        this.age = age;
        this.salary = salary;
        this.post = post;
        this.contact = contact;
        
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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
    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

  

    public String getContact() {
        return contact;
    }

}
