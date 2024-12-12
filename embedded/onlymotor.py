
from adafruit_servokit import ServoKit

from time import sleep                        
from gpiozero import Button                   
from gpiozero import DistanceSensor

import time
import smbus2
import busio
import board
import requests

i2c_bus=busio.I2C(board.SCL, board.SDA)
kit=ServoKit(channels=16,i2c=i2c_bus, address=0x60)



kit.servo[0].angle = 180
kit.continuous_servo[1].throttle = 1
time.sleep(1)
kit.continuous_servo[1].throttle = -1
time.sleep(1)
sleep(1)
                  
      #    response=requests.post(url=REQUEST_URL,json=json)


kit.servo[0].angle = 0
kit.continuous_servo[1].throttle = 0




