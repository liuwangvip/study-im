package com.isoler.studyim.business.chatmessage.model.dto;

import com.isoler.studyim.common.model.dto.PageQo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description
 *
 * @author liuwang
 * @Date 2022-10-06
 */
@Data
@Accessors(chain = true)
@ApiModel("聊天记录查询")
public class ChatMessageDto extends PageQo {
    /**
     * 聊天内容查询
     */
    @ApiModelProperty("聊天内容")
    private String content;

    /**
     * 聊天内容查询
     */
    @ApiModelProperty("搜索聊天内容")
    private String searchText;
}
