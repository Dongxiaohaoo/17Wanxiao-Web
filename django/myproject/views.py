#!
import json

from django.http import HttpResponse
from login.campus import CampusLogin
from wanxiao import index
# from api import *
# from utils import *

def hello(request):
    return HttpResponse("Hello world!")


def pwd_login(request):
    phone = request.GET.get("phone")
    pwd = request.GET.get("pwd")
    dev = request.GET.get("dev")
    cl = CampusLogin(phone,dev)
    status = cl.pwd_login(pwd)
    # return JsonResponse(status)
    return HttpResponse(
        json.dumps(status, ensure_ascii=False),
        content_type="application/json,charset=utf-8"
    )

def send_sms(request):
    phone = request.GET.get("phone")
    dev = request.GET.get("dev")
    cl = CampusLogin(phone,dev)
    status = cl.send_sms()
    return HttpResponse(
        json.dumps(status, ensure_ascii=False),
        content_type="application/json,charset=utf-8"
    )

def sms_login(request):
    phone = request.GET.get("phone")
    dev = request.GET.get("dev")
    sms = request.GET.get("sms")
    cl = CampusLogin(phone, dev)
    status = cl.sms_login(sms)
    return HttpResponse(
        json.dumps(status, ensure_ascii=False),
        content_type="application/json,charset=utf-8"
    )

def auto_card(request):
    phone = request.GET.get("phone")
    password = request.GET.get("password")
    device_id = request.GET.get("device_id")
    template = request.GET.get("template")
    email = request.GET.get("email")
    if template == "1":
        healthy_checkin = {"one_check": True, "two_check": False}
    elif template == "2":
        healthy_checkin = {"one_check": False, "two_check": True}
    status = index.main_handler(phone, password, device_id, healthy_checkin,email)
    return HttpResponse(
        json.dumps(status, ensure_ascii=False),
        content_type="application/json,charset=utf-8"
    )
