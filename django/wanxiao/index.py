from api.healthy1_check import get_healthy1_check_post_json, healthy1_check_in
from api.healthy2_check import get_healthy2_check_posh_json, healthy2_check_in
from api.user_info import get_user_info
from api.wanxiao_push import (
    wanxiao_qmsg_push,
    wanxiao_email_push,
)
from login.campus import CampusLogin
from setting import log

def get_token(username, password, device_id):
    try:
        campus_login = CampusLogin(phone_num=username, device_id=device_id)
    except Exception as e:
        log.warning(e)
        return None
    login_dict = campus_login.pwd_login(password)
    if login_dict["status"]:
        log.info(f"{username[:4]}，{login_dict['msg']}")
        return login_dict["token"]
    else:
        log.warning(f"{username[:4]}，{login_dict['errmsg']}")
        return None


def info_push(push_dict, raw_info):
    push_funcs = {
        "email": wanxiao_email_push,
        "qmsg": wanxiao_qmsg_push,
    }

    push_raw_info = {"check_info_list": raw_info}
    for push_name, push_func in push_funcs.items():
        # print(push_name+"---"+push_func)
        enable = push_dict.get(push_name, {}).get("enable")
        if not enable:
            pass
        else:
            del push_dict[push_name]["enable"]
            push_dict[push_name].update(push_raw_info)
            params_dict = push_dict[push_name]
            push_res = push_func(**params_dict)
            if push_res["status"]:
                log.info(push_res["msg"])
            else:
                log.warning(push_res["errmsg"])


def check_in(user):
    check_dict_list = []

    # 登录获取token用于打卡
    token = get_token(user["phone"], user["password"], user["device_id"])

    if not token:
        errmsg = f"{user['phone'][:4]}，获取token失败，打卡失败，"
        log.warning(errmsg)
        check_dict_list.append({"status": 0, "errmsg": errmsg})
        return check_dict_list

    # 获取个人信息
    user_info = get_user_info(token)
    if not user_info:
        errmsg = f"{user['phone'][:4]}，获取个人信息失败，打卡失败"
        log.warning(errmsg)
        check_dict_list.append({"status": 0, "errmsg": errmsg})
        return check_dict_list
    log.info(f'{user_info["username"][0]}-{user_info["school"]}，获取个人信息成功')

    healthy1_check_config = user.get("healthy_checkin", {}).get("one_check")
    healthy2_check_config = user.get("healthy_checkin", {}).get("two_check")
    if healthy1_check_config:
        # 第一类健康打卡

        # 获取第一类健康打卡的参数
        post_dict = get_healthy1_check_post_json(token)

        # 合并配置文件的打卡信息
        # merge_post_json(post_dict, healthy1_check_config.get("post_json", {}))

        healthy1_check_dict = healthy1_check_in(token, user["phone"], post_dict)
        check_dict_list.append(healthy1_check_dict)
    elif healthy2_check_config:
        # 第二类健康打卡
        # 获取第二类健康打卡参数
        post_dict = get_healthy2_check_posh_json(token)

        healthy2_check_dict = healthy2_check_in(token, user_info["customerId"], post_dict)

        check_dict_list.append(healthy2_check_dict)
    else:
        log.info("当前并未配置健康打卡方式，暂不进行打卡操作")

    print(check_dict_list)
    return check_dict_list


def main_handler(phone, password, device_id, healthy_checkin,email):
    # 推送数据
    raw_info = []
    # 单人打卡
    user_config = {
        "phone": phone,
        "password": password,
        "device_id": device_id,
        "healthy_checkin": healthy_checkin,
        "push": {
            "email": {
                "enable": True,
                "smtp_address": "smtp.qq.com",
                "smtp_port": 465,
                "send_email": "邮箱",
                "send_pwd": "邮箱授权码",
                "receive_email": email
            },
            "qmsg": {
                "enable": False,
                "key": "**",
                "type": "send",
                "qq_num": "**"
            }
        }
    }
    # print(user_config.get("healthy_checkin", {}).get("one_check"))
    check_dict = check_in(user_config)
    # 单人推送
    info_push(user_config["push"], check_dict)
    raw_info.extend(check_dict)
    return check_dict

# if __name__ == "__main__":
#     phone = ""
#     password = ""
#     device_id = "614534617750992"
#     template = "1"
#     if template == "1":
#         healthy_checkin = {"one_check":True,"two_check":False}
#     elif template == 2:
#         healthy_checkin = {"one_check":False,"two_check":True}
#     main_handler(phone,password,device_id,healthy_checkin)
