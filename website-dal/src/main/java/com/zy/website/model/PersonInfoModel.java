package com.zy.website.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_person_info")
public class PersonInfoModel extends Model<PersonInfoModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 中文名
     */
    @TableField("ch_name")
    private String chName;

    /**
     * 外文名
     */
    @TableField("en_name")
    private String enName;

    /**
     * 籍贯
     */
    @TableField("hometown")
    private String hometown;

    /**
     * 出生日期
     */
    @TableField("born_time")
    private Date bornTime;

    /**
     * 身高
     */
    @TableField("height")
    private Integer height;

    /**
     * 民族
     */
    @TableField("ethnic")
    private String ethnic;

    /**
     * 职业
     */
    @TableField("profession")
    private String profession;

    /**
     * 近期照片
     */
    @TableField("recent_photos")
    private String recentPhotos;

    /**
     * 视频职业 SCENARIST:编剧 STAR:主演
     */
    @TableField("film_profession")
    private String filmProfession;

    /**
     * 视频uid
     */
    @TableField("film_uid")
    private String filmUid;

    /**
     * 创建时间
     */
    @TableField("creact_time")
    private Date creactTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
