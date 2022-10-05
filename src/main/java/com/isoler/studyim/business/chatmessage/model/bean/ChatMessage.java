package com.isoler.studyim.business.chatmessage.model.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.isoler.studyim.business.chatmessage.model.eo.MessageStatusEnum;
import com.isoler.studyim.common.model.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天消息
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("db_chat.t_chat_message")
@ApiModel(value = "ChatMessage对象", description = "聊天消息")
public class ChatMessage extends BaseEntity {

    @ApiModelProperty(value = "发送目标类型（个人，聊天室)")
    @TableField("c_target_type")
    private String targetType;

    @ApiModelProperty(value = "消息类型(提示，文字，图片，文件）")
    @TableField("c_type")
    private String type;

    @ApiModelProperty(value = "消息发送者id")
    @TableField("c_sender_id")
    private String senderId;

    @ApiModelProperty(value = "消息发送者名称")
    @TableField("c_sender_name")
    private String senderName;

    @TableField("c_target_id")
    private String targetId;

    @ApiModelProperty(value = "消息内容")
    @TableField("c_content")
    private String content;

    @ApiModelProperty(value = "文件id")
    @TableField("c_file_id")
    private String fileId;

    @ApiModelProperty(value = "文件名称")
    @TableField("c_file_name")
    private String fileName;

    @ApiModelProperty(value = "消息是否失效,默认有效")
    @TableField("c_status")
    private String status = MessageStatusEnum.VALID.getStatus();




}
