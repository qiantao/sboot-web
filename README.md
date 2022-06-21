# qt

记录待了解
@ConditionalOnProperty

# 模糊查询替换关键字
and t.`name` like
CONCAT('%',replace(replace(#{productNameKeyword},"_","\_") ,"%","\%"),'%')
