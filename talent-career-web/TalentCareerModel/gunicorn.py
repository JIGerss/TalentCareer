import os

# 设置守护进程
daemon = True
# 监听内网端口7777
bind = '0.0.0.0:7777'
# 设置进程文件目录
pidfile = './gunicorn.pid'
chdir = './'  # 工作目录
# 工作模式
worker_class = 'uvicorn.workers.UvicornWorker'
# 并行工作进程数 核心数*2+1个
workers = 3  # multiprocessing.cpu_count()+1
# 指定每个工作者的线程数
threads = 2
# 设置最大并发量
worker_connections = 2000
# 设置超时时间
timeout = 240
loglevel = 'debug'  # 错误日志的日志级别
access_log_format = '%(t)s %(p)s %(h)s "%(r)s" %(s)s %(L)s %(b)s %(f)s" "%(a)s"'
# 设置访问日志和错误信息日志路径
log_dir = "./logs"
if not os.path.exists(log_dir):
    os.makedirs(log_dir)
accesslog = "./logs/access.log"
errorlog = "./logs/TalentCareer.log"