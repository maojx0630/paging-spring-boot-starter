# page-spring-boot-starter
## 使用方法
本项目依赖于spring-boot-starter-web，必须web环境才可以使用 只支持mysql
## 开启分页方法
优先级从上到下 越靠上级别越高  
* 使用PageUtils.start开启分页 (使用该方法后,应立即执行sql,若未执行可能会分页异常或者内存泄漏)  
* 开启EnablePage注解  
    * 若设置pageSizeInt则会优先读取该数值
    * isEnablePageCount 为是否获取总数 默认开启
    * pageSize与pageNo为request中的参数名
* 传入PageAbelQuick对象(PageAbel已实现该接口,该对象可以通过手动传入参数构造,也可以通过实现PageAbelQuick接口的对象快速构造)多个时最靠前的参数生效  
## 获取分页信息
传入参数为mapper返回值  
```Page<T> page=PageUtils.get(List);```
## 可根据自己需求进行定制化修改
可通过PageInterceptor中的getCountSql修改获取总数sql  
可通过PageInterceptor中的getPageSql修改分页sql  