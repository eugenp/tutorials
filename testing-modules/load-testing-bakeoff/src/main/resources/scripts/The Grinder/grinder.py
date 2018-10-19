from net.grinder.script import Test
from net.grinder.script.Grinder import grinder
from net.grinder.plugin.http import HTTPRequest, HTTPPluginControl, HTTPUtilities
from HTTPClient import NVPair

test1 = Test(1, "Request resource")
request1 = HTTPRequest()
headers = \
( NVPair('Content-Type', 'application/json'), )
request1.setHeaders(headers)
utilities = HTTPPluginControl.getHTTPUtilities()
test1.record(request1)

class TestRunner:
    def __call__(self):
        customerId = "4321";
        result = request1.POST("http://localhost:8080/transactions/add", "{"'"customerRewardsId"'":null,"'"customerId"'":4321,"'"transactionDate"'":null}")
        result = request1.GET("http://localhost:8080/rewards/find/4321")
        result = request1.POST("http://localhost:8080/rewards/add", "{"'"customerId"'":4321}")
        result = request1.POST("http://localhost:8080/transactions/add", "{"'"customerRewardsId"'":1,"'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
        result = request1.GET("http://localhost:8080/transactions/findAll/1")