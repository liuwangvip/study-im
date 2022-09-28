package com.isoler.studyim.business.chatroom.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import com.isoler.studyim.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天室
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("db_chat.t_chat_room")
@ApiModel(value="ChatRoom对象", description="聊天室")
public class ChatRoom extends BaseEntity {



    @ApiModelProperty(value = "聊天室名称")
    @TableField("c_name")
    private String name;

    @ApiModelProperty(value = "公告")
    @TableField("c_topic")
    private String topic;

    @ApiModelProperty(value = "聊天室描述")
    @TableField("c_description")
    private String description;



}
