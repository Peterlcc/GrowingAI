# -*-coding:utf-8-*-
import json
import requests


class Result():
    def __init__(self, length, points,time, projectId):
        self.length = length
        self.points = points
        self.time = time
        self.projectId = projectId

    def __str__(self):
        return json.dumps(self.__dict__, encoding="utf-8")


def post(url, param):
    print "url:"+url
    print param
    res = requests.post(url=url, data=param)
    print res.text


if __name__ == "__main__":
    res = Result(10.5, 5, 3, 1)
    print res
    post("http://localhost:8080/growningai/result/save", res.__dict__)
