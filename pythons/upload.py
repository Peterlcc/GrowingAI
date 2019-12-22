# -*-coding:utf-8-*-
import json
import requests


class Result():
    def __init__(self, length, points, time, projectId):
        self.length = length
        self.points = points
        self.time = time
        self.projectId = projectId

    def __str__(self):
        return json.dumps(self.__dict__, encoding="utf-8")


def post(url, param):
    print "url:"+url
    data = {}
    data['results'] = param
    print param
    res = requests.post(url=url, data=data)
    print res.text


if __name__ == "__main__":
    l = []
    l.append(Result(10.1, 5, 3.5, 1).__dict__)  # 将对象的字典加入list
    l.append(Result(10.2, 5, 3.5, 1).__dict__)
    l.append(Result(10.3, 5, 3.5, 1).__dict__)
    print l
    post("http://localhost:8888/growningai/result/save",
         str(l))  # list转字符串，构成json数据格式
