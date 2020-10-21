# -*-coding:utf-8-*-
import json
import requests
'''为结果的上传设计的工具类'''
CONTEXT_URL="http://localhost:8999/growningai"
LOGIN_URL=CONTEXT_URL+"/admin/login"
SAVE_URL=CONTEXT_URL+"/result/save"

class Result():
    def __init__(self, length, points, time, projectId,datasetId):
        self.length = length
        self.points = points
        self.time = time
        self.projectId = projectId
        self.datasetId=datasetId
    def __init__(self, length, points, time, projectId):
            self.length = length
            self.points = points
            self.time = time
            self.projectId = projectId

    def __str__(self):
        return json.dumps(self.__dict__, encoding="utf-8")

def post(url,param):
    sess=requests.session()
    res=sess.post(LOGIN_URL,{"name":"admin","password":"buaagrown"})
    res=sess.post(SAVE_URL,{"data":param})  # list转字符串，构成json数据格式

if __name__ == "__main__":
    l = []
    l.append(Result(10.1, 5, 3.5, 1).__dict__)  # 将对象的字典加入list
    l.append(Result(10.2, 5, 3.5, 1).__dict__)
    l.append(Result(10.3, 5, 3.5, 1).__dict__)
    # print l
    sess=requests.session()
    res=sess.post("http://localhost:8999/growningai/admin/login",{"name":"admin","password":"buaagrow"})
    res=sess.post("http://localhost:8999/growningai/result/save",{"results":str(l)})  # list转字符串，构成json数据格式
