CREATE TABLE `t_dashboard_scheduler_info` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
      `scheduler_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务key',
      `scheduler_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
      `cron` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行表达式',
      `retry_time` int NOT NULL DEFAULT '1' COMMENT '重试次数',
      `current_exec_time` datetime DEFAULT NULL COMMENT '最近执行时间',
      `next_exec_time` datetime DEFAULT NULL COMMENT '下一次执行时间',
      `result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行结果',
      `creator_id` bigint DEFAULT NULL COMMENT '创建人',
      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `modifier_id` bigint DEFAULT NULL COMMENT '修改人',
      `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `uk_schedulerkey` (`scheduler_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务信息表';