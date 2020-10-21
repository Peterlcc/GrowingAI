import redis
import json
import time as system_time

class RedisUtil:
    '''redis的工具类，需要设置redis的key和主机地址host，port默认6379'''
    def __init__(self,host='127.0.0.1',port=6379,password='',db=1):
        #初始化连接池
        self.pool=redis.ConnectionPool(host=host,port=port,password=password,db=db)
        #得到连接
        self.redisClient=redis.Redis(connection_pool=self.pool)

    def publish(self,key,data,className):
        '''向key的队列发布数据'''
        #将对象序列化为json对象
        all_data=[]
        all_data.append(className)
        all_data.append(data.__dict__)
        jsonData = json.dumps(all_data)

        key_exist=True
        if not self.redisClient.exists(key):
            key_exist=False

        #插入key的队列
        self.redisClient.rpush(key,jsonData)
        
        if not key_exist:
            self.redisClient.expire(key,60*60*24*7)#列表第一次创建时设置过期时间为7天

    def subscribe(self,key):
        '''订阅key队列的任务，注意此方法返回的是json数据，不是对象'''
        data = self.redisClient.lpop(key)
        return data

class Result:
    '''算法结果记录类'''
    def __init__(self,length,points,time):
        self.length=length
        self.points=points
        self.time=time

        self.timestamp=int(round(system_time.time()*1000))#毫秒级时间戳

    def __str__(self):
        return "Result:[length="+self.length+",points="+self.points+",time="+self.time+",timestamp="+self.timestamp+"]"

if __name__ == '__main__':
    className='com.peter.bean.AlgorithmData'
    result = Result(10.2, 12, 15.5)
    redis_util = RedisUtil()
    redis_util.publish("res_0",result,className)
    redis_util.publish("res_0",result,className)
    redis_util.publish("res_0",result,className)