package entity;

import java.io.Serializable;

public class StudentEntity implements Serializable {

    // 学号
    private Integer id;

    // 姓名
    private String name;

    // 年龄
    private Integer age;

    // 班级
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StudentEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", className='").append(className).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
