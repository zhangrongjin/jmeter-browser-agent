package com.platon.agent.utils;

import lombok.Data;

/**
 * @Auther: Chendongming
 * @Date: 2019/9/10 12:05
 * @Description:
 */
@Data
public class ProposalParticiantStat {
    private Long voterCount; // 累积可投票人数
    private Long supportCount; // 赞成票数
    private Long opposeCount; // 反对票数
    private Long abstainCount; // 弃权票数
}
