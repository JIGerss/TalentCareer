import re
from datetime import date


# 预处理函数
def preprocessing(irregular):
    # 先去除空格,转换符号
    a = irregular.replace(" ", "").replace("－", "-").replace("–", "-")

    # 去除其他无效文字
    allowed_pattern = r"[\d年月日\-.]"
    a = re.sub(f"[^{allowed_pattern}]", "", a)

    # 如果有"-"，则分割成两部分
    if "-" in a:
        arr = a.split("-")
    else:
        arr = [a]
    return arr


# 标准化日期
def normalize_date(date_string):
    # 先预处理
    date_arr = preprocessing(date_string)

    normalize_date_arr = []
    for date in date_arr:
        # 匹配 xxxx年 格式
        pattern11 = r'(\d{4})年'
        # 匹配xxxx年xx月 格式
        pattern12 = r'(\d{4})年(\d{1,2})月?'
        # 匹配xxxx年xx月xx日 格式
        pattern13 = r'(\d{4})年(\d{1,2})月(\d{1,2})日?'

        # 匹配 xxxx.xx.xx 格式
        pattern31 = r'(\d{4})\.(\d{1,2})\.(\d{1,2})'
        # 匹配 xxxx.xx 格式
        pattern32 = r'(\d{4})\.(\d{1,2})'

        # 匹配 xx–xxxx 格式
        pattern41 = r'(\d{1,2})–(\d{4})'

        # 标准化为 xxxx-xx-xx 形式
        def standardize_date(match):
            groups = match.groups()
            return f'{groups[0]}-{groups[1].zfill(2)}-{groups[2].zfill(2)}'

        # 标准化为 xxxx-xx 形式
        def standardize_month(match):
            groups = match.groups()
            return f'{groups[0]}-{groups[1].zfill(2)}'

        # 标准化为 xxxx 形式
        def standardize_year(year):
            groups = year.groups()
            return f'{groups[0]}'

        # 依次匹配并标准化不同的日期格式
        if re.match(pattern13, date):  # 如果匹配成功，直接结束当前循环
            date = re.sub(pattern13, standardize_date, date)
            normalize_date_arr.append(date)
            continue

        if re.match(pattern12, date):
            date = re.sub(pattern12, standardize_month, date)
            normalize_date_arr.append(date)
            continue

        if re.match(pattern11, date):
            date = re.sub(pattern11, standardize_year, date)
            normalize_date_arr.append(date)
            continue

        if re.match(pattern31, date):
            date = re.sub(pattern31, standardize_date, date)
            normalize_date_arr.append(date)
            continue

        if re.match(pattern32, date):
            date = re.sub(pattern32, standardize_month, date)
            normalize_date_arr.append(date)
            continue

        if re.match(pattern41, date):
            date = re.sub(pattern41, r'\2-\1', date)  # 调整顺序并标准化为 xxxx-xx 形式
            normalize_date_arr.append(date)
            continue

    return normalize_date_arr


# 对日期进行从早到晚排序
def sort_dates(dates):
    sorted_dates = sorted(dates, key=lambda x: [int(d) for d in x.split('-') if d.isdigit()])
    return sorted_dates


# 计算年份差异
def calculate_year_difference(date1, date2):
    date1_parts = date1.split("-")
    date2_parts = date2.split("-")

    year1 = int(date1_parts[0])
    year2 = int(date2_parts[0])

    if len(date1_parts) > 1:
        month1 = int(date1_parts[1])
    else:
        month1 = 1

    if len(date2_parts) > 1:
        month2 = int(date2_parts[1])
    else:
        month2 = 1

    difference = year2 - year1

    if month2 < month1:
        difference -= 1

    return difference


# 获取xxxx-xx-xx格式的当前日期
def get_current_date():
    current_date = date.today()
    formatted_date = current_date.strftime("%Y-%m-%d")
    return formatted_date
