# vCampus
本项目设计目的是打造校园智能管理系统，项目包含 service 端和 client 端。             



## Env
jdk 1.8    

GBK编码



## vCampusService
service 端用于实现前端和后端请求和响应的交互。

修改 src/utils/DBHelper 中 mysql 用户的信息，导入 data.sql

运行 src/service

这里想用GPT功能可以填自己的密钥，见 src/utils/gpt_yun



## vCampusClient
client 端面向用户:管理员、学生、教师

修改 src/utils/SocketHelper 中的服务器 ip，服务器利用cmd,ipconfig可以获得服务器 ip

运行 src/StartLogin 开始



## 功能
权限自动分配：管理员、用户、老师

用户管理、登录

学籍管理模块

图书馆模块：图书管理、文献管理

选课模块：排课，选课，打分

商店模块：添加商品，购物

聊天模块：(类似qq) 多页面聊天，文本、图片、文件、语音通话、GPT      


