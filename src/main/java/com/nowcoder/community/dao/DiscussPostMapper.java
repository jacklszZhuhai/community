package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(@Param("userId")int userId, @Param("offset")int offset, @Param("limit")int limit);

    // 查询表中一共有多少数据
    // @Param用于给参数取别名
    // 如果方法只有一个参数，并且在<if>里使用，那必须加别名
    int selectDiscussPostRows(@Param("userId")int userId);

}
