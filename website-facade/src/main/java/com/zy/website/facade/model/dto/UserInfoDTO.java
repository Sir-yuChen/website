package com.zy.website.facade.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {

    private Integer id;
    private String uid;
    private String uname;
    private Integer age;
    private String uphone;
    private Integer addressid;
    private String createdatetime;
    private String status;
    private String gender;
    private String idcardno;
    private String cardurl;
    private String mail;
    private String avatarImgUrl;
    private Integer loginType;
    private String loginDevice;
    private List<TagLabelDTO> tagList;
    private String placeNow;
    private String company;
    private String position;
    private String motto;

}
