```
redis-cli -u redis://default:7GSwG7XlyUhxW9sXKmRDDH28XSAahF4b@redis-11177.c252.ap-southeast-1-1.ec2.cloud.redislabs.com:11177
```

save this to application.properties
```
spring.redis.host=redis-11177.c252.ap-southeast-1-1.ec2.cloud.redislabs.com
spring.redis.port=11177
spring.redis.password=7GSwG7XlyUhxW9sXKmRDDH28XSAahF4b
spring.redis.database=0
spring.jackson.property-naming-strategy=SNAKE_CASE
```