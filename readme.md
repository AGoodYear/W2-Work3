# West2-Online Java方向第三轮

# 技术栈

Java、Mysql

# 项目结构

utils下存放用于连接数据库和释放连接的类

dao下存放对商品和订单进行增删改查的类

service下存放功能选择的函数

└─src
    ├─dao
    ├─pojo
    ├─service
    └─utils

# 功能介绍

项目可以对商品、订单进行增删改查，同时支持订单以下单时间和价格进行排序。相关信息存储到数据库中。

# 项目待改进点

对于异常处理支持的不够好

代码复用性不高

