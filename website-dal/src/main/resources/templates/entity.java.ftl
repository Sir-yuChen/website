package ${package.Entity};

<#--import java.util.Date;-->
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
<#list table.importPackages as pkg>
    <#if pkg == "java.math.BigDecimal">
        import ${pkg};
    </#if>
    <#if pkg == "java.time.LocalDateTime">
        import java.util.Date;
    </#if>
</#list>

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
*  ${table.name} : ${table.comment!}
*  @author ${author}
*  @since ${date}
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("${table.name}")
public class ${entity} extends Model<${entity}> implements Serializable {

private static final long serialVersionUID = 1L;
<#-- ----------  属性私有化  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.keyFlag>
    <#-- 主键 -->
        /**
        * 主键 : ${field.name}  ${field.comment!}
        */
     @TableId(value = "${field.name}", type = IdType.AUTO)
    <#-- 普通字段 -->
    <#elseif !field.keyFlag>
        /**
        * ${field.name}: ${field.comment!}
        */
    </#if>
<#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
        @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
        @TableLogic
    </#if>
    <#if field.propertyType == "LocalDateTime">
        private Date ${field.propertyName};
    </#if>
    <#if field.propertyType != "LocalDateTime">
        private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

@Override
protected Serializable pkVal() {
return this.id;
}
}

