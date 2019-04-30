# page-spring-boot-starter
# 使用方法
clone项目后进入项目 运行  mvn package 将jar上传到私服引入即可
也可复制page目录下代码放入spring boot扫描路径中
# 开启分页方法
优先级从上到下 越靠上级别越高  
1.使用PageUtils.start开启分页 (使用该方法后,应立即执行sql,若未执行可能会分页异常或者内存泄漏)  
2.开启EnablePage注解  
3.传入PageAbelQuick对象(PageAbel已实现该接口,该对象可以通过手动传入参数构造,也可以通过实现PageAbelQuick接口的对象快速构造)多个时最靠前的参数生效  
# 获取分页信息
传入参数为mapper返回值  
```Page<T> page=PageUtils.get(List);```
# 可根据自己需求进行定制化修改
可通过PageInterceptor中的getCountSql修改获取总数sql  
可通过PageInterceptor中的getPageSql修改分页sql  