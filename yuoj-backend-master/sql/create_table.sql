create table post
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '帖子' collate = utf8mb4_unicode_ci;

create index idx_userId
    on post (userId);

create table post_favour
(
    id         bigint auto_increment comment 'id'
        primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子收藏';

create index idx_postId
    on post_favour (postId);

create index idx_userId
    on post_favour (userId);

create table post_thumb
(
    id         bigint auto_increment comment 'id'
        primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子点赞';

create index idx_postId
    on post_thumb (postId);

create index idx_userId
    on post_thumb (userId);

create table question
(
    id          bigint auto_increment comment 'id'
        primary key,
    title       varchar(512)                       null comment '标题',
    content     text                               null comment '内容',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    answer      text                               null comment '题目答案',
    submitNum   int      default 0                 not null comment '题目提交数',
    acceptedNum int      default 0                 not null comment '题目通过数',
    judgeCase   text                               null comment '判题用例（json 数组）',
    judgeConfig text                               null comment '判题配置（json 对象）',
    thumbNum    int      default 0                 not null comment '点赞数',
    favourNum   int      default 0                 not null comment '收藏数',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '题目' collate = utf8mb4_unicode_ci;

create index idx_userId
    on question (userId);

create table question_comment
(
    id         bigint auto_increment comment 'id'
        primary key,
    questionId bigint                               not null comment '题目 id',
    userId     bigint                               not null comment '创建用户 id',
    content    varchar(1024)                        null comment '评论',
    pid        bigint                               null comment '评论等级（所属等级）',
    createTime datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    commentid  bigint                               null comment '回复用户id',
    requested  tinyint(1) default 0                 null,
    commentids bigint                               null comment '回复评论id'
)
    comment '评论表';

create index idx_pid
    on question_comment (pid);

create index idx_questionId
    on question_comment (questionId);

create index idx_userId
    on question_comment (userId);

create table question_likes
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint                             not null comment '创建用户 id',
    commentId  bigint                             null comment '评论 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '点赞表';

create index idx_questionId
    on question_likes (userId);

create index idx_userId
    on question_likes (commentId);

create table question_submit
(
    id         bigint auto_increment comment 'id'
        primary key,
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '用户代码',
    judgeInfo  text                               null comment '判题信息（json 对象）',
    status     int      default 0                 not null comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    questionId bigint                             not null comment '题目 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '题目提交';

create index idx_questionId
    on question_submit (questionId);

create index idx_userId
    on question_submit (userId);

create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                                                                                                                                                         not null comment '账号',
    userPassword varchar(512)                                                                                                                                                         not null comment '密码',
    unionId      varchar(256)                                                                                                                                                         null comment '微信开放平台id',
    mpOpenId     varchar(256)                                                                                                                                                         null comment '公众号openId',
    userName     varchar(256)  default '用户'                                                                                                                                         null comment '用户昵称',
    userAvatar   varchar(1024) default 'https://img.zcool.cn/community/01a6095f110b9fa8012066219b67d4.png?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100' null comment '用户头像',
    userProfile  varchar(512)                                                                                                                                                         null comment '用户简介',
    userRole     varchar(256)  default 'user'                                                                                                                                         not null comment '用户角色：user/admin/ban',
    createTime   datetime      default CURRENT_TIMESTAMP                                                                                                                              not null comment '创建时间',
    updateTime   datetime      default CURRENT_TIMESTAMP                                                                                                                              not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint       default 0                                                                                                                                              not null comment '是否删除',
    qqEmail      varchar(256)                                                                                                                                                         null comment 'QQ邮箱'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (unionId);

