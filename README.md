# paging-spring-boot-starter
## 使用方法
spring boot web引入
```xml
<dependency>
   <groupId>com.github.maojx0630</groupId>
   <artifactId>paging-spring-boot-starter</artifactId>
   <version>1.4</version>
</dependency>
```
非spring web项目 引入
```xml
<dependency>
   <groupId>com.github.maojx0630</groupId>
   <artifactId>paging-spring-boot-starter</artifactId>
   <version>1.4-NoWeb</version>
</dependency>
```

## 开启分页方法
优先级从上到下 越靠上级别越高  
* 使用PageUtils.start开启分页  
* 传入PageAbelQuick对象(PageAbel已实现该接口,该对象可以通过手动传入参数构造,也可以通过实现PageAbelQuick接口的对象快速构造)多个时最靠前的参数生效 
* 开启EnablePage注解（非web情况下不可用）  
    * 若设置pageSizeInt则会优先读取该数值
    * isEnablePageCount 为是否获取总数 默认开启
    * pageSize与pageNo为request中的参数名 
## 获取分页信息
传入参数为mapper返回值  
```
Page<T> page=PageUtils.get(List);
```
## 可根据自己需求进行定制化修改
默认支持mysql 若需支持其他类型 请实现 SqlCountAndPaging 接口并添加 Component 注解
