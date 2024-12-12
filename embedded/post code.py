import requests


r = requests.get('http://date.jsontest.com/')

SERVER_IP='i11c101.p.ssafy.io'

REQUEST_URL='https://i11c101.p.ssafy.io/api/v1/trash/catlog'


json={
        'serialNumber':"test3",
        'categoryId':3
    }

response=requests.post(url=REQUEST_URL,json=json)



print(requests)
print('request finished')

