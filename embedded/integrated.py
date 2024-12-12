from adafruit_servokit import ServoKit

from time import sleep                        
from gpiozero import Button                   
from gpiozero import DistanceSensor

import time
import smbus2
import busio
import board
import requests


r = requests.get('http://date.jsontest.com/')
SERVER_IP='i11c101.p.ssafy.io'
REQUEST_URL='https://i11c101.p.ssafy.io/api/v1/trash/rest'

json={
        'serialNumber':"test3",
        'restPercent':80
    }

i2c_bus=busio.I2C(board.SCL, board.SDA)
kit=ServoKit(channels=16,i2c=i2c_bus, address=0x60)
sensor = DistanceSensor (echo=23, trigger=24)



print("good")
  
button = Button(18)                           
  
while True:
     if button.is_pressed:                    
          print("Button is pressed")
          

          kit.servo[0].angle = 180
          kit.continuous_servo[1].throttle = 1
          time.sleep(1)
          kit.continuous_servo[1].throttle = -1
          time.sleep(1)
          
                  
   #       response=requests.post(url=REQUEST_URL,json=json)
          kit.servo[0].angle = 0
          kit.continuous_servo[1].throttle = 0
          time.sleep(1)
          kit.servo[0].angle = 180
          kit.continuous_servo[1].throttle = 1
          time.sleep(1)
          kit.continuous_servo[1].throttle = -1
          time.sleep(1)
          sleep(1)
                  
      #    response=requests.post(url=REQUEST_URL,json=json)
          
          distance=sensor.distance*100
          print(f"Distance: {distance:.2f} cm")

          kit.servo[0].angle = 0
          kit.continuous_servo[1].throttle = 0





          sleep(1)                         
     else:                                   
          print("Button is not pressed")       
          sleep(0.1) 



