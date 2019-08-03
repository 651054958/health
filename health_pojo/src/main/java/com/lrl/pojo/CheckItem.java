package com.lrl.pojo;

import java.io.Serializable;

public class CheckItem implements Serializable {

  private Integer id;
  private String code;
  private String name;
  private String sex;
  private String age;
  private Double price;
  private String type;
  private String attention;
  private String remark;


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getAttention() {
    return attention;
  }

  public void setAttention(String attention) {
    this.attention = attention;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "CheckItem{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", sex='" + sex + '\'' +
            ", age='" + age + '\'' +
            ", price=" + price +
            ", type='" + type + '\'' +
            ", attention='" + attention + '\'' +
            ", remark='" + remark + '\'' +
            '}';
  }
}
