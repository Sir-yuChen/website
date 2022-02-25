package com.zy.website.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {
//@JsonFormat就可以实现对枚举值以对象的方式进行序列化 你可以把枚举的每个枚举值看做是一个对象,因为每个枚举值都有code和message属性 再为其code和message属性添加get方法 就可以了
}
