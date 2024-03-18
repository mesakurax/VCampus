# vCampus
The design purpose of this project is to build an intelligent campus management system. The project includes service side and client side.



## Env
jdk 1.8

GBK encoding



## vCampusService
The service end is used to implement the interaction of front-end and back-end requests and responses.

Modify the mysql user information in `src/utils/DBHelper` and import `data.sql`

Run `src/service`

If you want to use the GPT function here, you can fill in your own key, see `src/utils/gpt_yun`



## vCampusClient
The client side is for users: administrators, students, teachers

Modify the server ip in `src/utils/SocketHelper`. The server can obtain the server ip using cmd and `ipconfig`.

Run `src/StartLogin` to start



## Function
Permissions are automatically assigned: administrators, users, teachers

User management, login

Student status management module

Library module: library management, document management

Course selection module: course scheduling, course selection, and grading

Store module: add products, shop

Chat module: (similar to qq) multi-page chat, text, pictures, files, voice calls, GPT      


