from net.grinder.script import Test
from net.grinder.script.Grinder import grinder
from net.grinder.plugin.http import HTTPRequest, HTTPPluginControl, HTTPUtilities
from HTTPClient import NVPair
from random import random
#from org.json import *

test1 = Test(1, "Request resource")
request1 = HTTPRequest()
headers = \
( NVPair('Content-Type', 'application/json'), )
request1.setHeaders(headers)
utilities = HTTPPluginControl.getHTTPUtilities()
test1.record(request1)

def jsonParse(json):
    json.split
class TestRunner:
    def __call__(self):
        customerId = "4321";
            #grinder.logger.info("{"'"customerRewardsId"'":null,"'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
        result = request1.POST("http://localhost:8080/transactions/add", "{"'"customerRewardsId"'":null,"'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
	    #transactionId = result.getText()
        result = request1.GET("http://localhost:8080/rewards/find/"+customerId)
	#rewardId = result.getText()
	#if result.rewardId == null:
        result = request1.POST("http://localhost:8080/rewards/add", "{"'"customerId"'":"+ customerId + "}")
	#rewardId = result.getText()
	    #result = request1.POST("http://localhost:8080/transactions/add", "{""customerRewardsId"":"+rewardId+",""customerId"":"+ customerId + ",""transactionDate"":null}")
        result = request1.POST("http://localhost:8080/transactions/add", "{"'"customerRewardsId"'":1,"'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
        result = request1.GET("http://localhost:8080/transactions/findAll/1") #+rewardId)

