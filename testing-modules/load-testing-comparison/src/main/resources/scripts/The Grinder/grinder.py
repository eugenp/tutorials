import java.util.Random
from net.grinder.script import Test
from net.grinder.script.Grinder import grinder
from net.grinder.plugin.http import HTTPRequest, HTTPPluginControl, HTTPUtilities
from HTTPClient import NVPair

def parseJsonString(json, element):
  for x in json.split(","):
    pc = x.replace('"','').split(":")
    if pc[0].replace("{","") == element:
      ele = pc[1].replace("}","")
      return ele
    else:
      return ""

test1 = Test(1, "Request resource")
request1 = HTTPRequest()
headers = \
( NVPair('Content-Type', 'application/json'), )
request1.setHeaders(headers)
utilities = HTTPPluginControl.getHTTPUtilities()
test1.record(request1)
random=java.util.Random()

class TestRunner:
    def __call__(self):
        customerId = str(random.nextInt());

        result = request1.POST("http://localhost:8080/transactions/add", "{"'"customerRewardsId"'":null,"'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
        txnId = parseJsonString(result.getText(), "id")

        result = request1.GET("http://localhost:8080/rewards/find/"+ customerId)
        rwdId = parseJsonString(result.getText(), "id")

        if rwdId == "":
          result = request1.POST("http://localhost:8080/rewards/add", "{"'"customerId"'":"+ customerId + "}")
          rwdId = parseJsonString(result.getText(), "id")

        result = request1.POST("http://localhost:8080/transactions/add", "{"'"id"'":" + txnId + ","'"customerRewardsId"'":" + rwdId + ","'"customerId"'":"+ customerId + ","'"transactionDate"'":null}")
        result = request1.GET("http://localhost:8080/transactions/findAll/" + rwdId)