# 项目启动
- cos配置 src/main/resources/application.yml
- 邮箱配置 src/main/resources/application.yml
- 邮箱配置 com/yupi/yuoj/utils/MailUtils.java
- sql导入 sql/create_table.sql
- 数据库配置 src/main/resources/application.yml
- redis启动
- 项目初始化成功
# OJ项目优化

## 注册功能=QQ邮箱注册+验证码 -.-
- 1. 添加依赖
- 2. 配置邮件发送属性
- 3. 发送验证码
- 4. 接口定义
- 5. Session



### 二次优化



## 收藏功能 -.-
 建立用户ID和文章ID一对多的关系-》实现通过用户ID返回文章列表


 收藏功能：点击mysql增删改查

 添加收藏：自增
    
 取消收藏：自减


## 留言功能-.-
- 文章留言：
-
- 评论表中使用 用户id, 与用户表进行关联




表字段	字段说明
  留言表
  id       主键  
  topic_id 题目id
  content  评论内容
  from_uid 评论用户id
  pid     null | 回复id



 - 根据题目获取分页获取评论列表
  遍历 获取头像名称 以及调用回复表
  chiden:[]
  子评论数量
 - 添加评论
 - 删除评论 （管理员可以删除所有评论）
 - 根据pid获取子评论：分页（默认获取一条）
  



  点赞表

  id    
  comid      留言id  
  userid     用户id
  
  - 查询留言的点赞数
  - 判断用户是否点赞
  - 取消点赞：点赞（redis）



  对文章：
  点赞表：
    

  

    评论字段:{
  头像
  名称
  内容
  时间
  用户是否点赞 默认false

  
}


## 点赞功能 -.-

 建立点赞表 用户(user)ID和文章（question）ID一对多的关系


 点赞：自增
 
 取消点赞：自减


 收藏

 评论

## BeanUtils.copyProperties(user,uservo); 直接替换


## 判题字增，用户提交后，和用户成功后数据库字段自增 -.-

Qusetion:


题目提交数量: subimtNum

题目通过数量： accepteNum

 点赞数    thumbNum

 收藏数    favourNum

# 查看回复
- (用户id) {查询所有回复 判断每个回复列表，有个字段为是否回复} -》 用户回复界面
- (用户id，评论id) {判断每条评论是否被请求  请求为1 没请求为null}-》 用户是否查看回复
- (用户id，评论id) {查看回复的id，让用户的状态变为1}-》更改用户回复

# SQl

