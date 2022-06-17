#!
import index

phone = "17683817443"
password = "DONGhao123"
device_id = "614534617750992"
template = "1"
if template == "1":
    healthy_checkin = {"one_check": True, "two_check": False}
elif template == 2:
    healthy_checkin = {"one_check": False, "two_check": True}
# main_handler(phone, password, device_id, healthy_checkin)