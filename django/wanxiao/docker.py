import shutil
import sys
import os

from apscheduler.schedulers.blocking import BlockingScheduler


# 获取当前目录
workingdir = os.getcwd()
# 检查目录是否为空
if os.path.exists(os.path.join(workingdir, "conf", "user.json")) == False:
    # 释放文件到conf目录下
    print("1")
    for root, dicts, names in os.walk(os.path.join(workingdir, "conf-init")):
        for name in names:
            shutil.copy(os.path.join(workingdir, "conf-init", name), os.path.join(workingdir, "conf"))


def start():
    os.system("python /app/index.py")


print(os.getenv("hours"))
print(os.getenv("minutes"))

scheduler = BlockingScheduler(timezone="Asia/Shanghai")
scheduler.add_job(start, "cron", hour=str(os.getenv("hours")), minute=str(os.getenv("minutes")))
scheduler.start()
