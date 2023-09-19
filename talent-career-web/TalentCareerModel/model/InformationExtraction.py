from paddlenlp import Taskflow

from common import InformationExtractionResult
from common.date_function import normalize_date, sort_dates, calculate_year_difference, get_current_date
import re

schema = {'姓名', '出生日期', '年龄', '时间', '籍贯', '学历', '学校', '政治面貌', '荣誉证书', '求职目标'}
ie = Taskflow('information_extraction', schema=schema)


# 使用PaddleNLP对文本数据进行提取
def exec(file_name, content):
    # 定义实体关系抽取的schema
    data = ie(content)
    result = {}

    print(data)
    for item in data:
        for key, value in item.items():
            if key == '时间':
                last_date = ''
                # 匹配content文本内容中是否出现
                if re.search(r'至今', content):
                    last_date = get_current_date()
                # 提取出所有text中的内容
                text_list = [item['text'] for item in value]
                normalized_list = []
                # 标准化所有时间
                for date in text_list:
                    normalized_date = normalize_date(date)
                    normalized_list.extend(normalized_date)
                # 同时标准化出生日期，如果有
                if '出生日期' in result:
                    birthday = normalize_date(result['出生日期'])
                    # 判断出生日期是否在'时间'中，如果有则，去除
                    if birthday in normalized_list:
                        normalized_list.remove(birthday)

                # 对列表进行排序
                sort_list = sort_dates(normalized_list)
                first_date = sort_list[0]
                if len(last_date) == 0:
                    if len(sort_list) > 1:
                        last_date = sort_list[-1]
                    else:
                        last_date = get_current_date()

                working_years = calculate_year_difference(first_date, last_date)
                result['工作年限'] = working_years

            elif key == '出生日期':
                if '年龄' not in result:
                    birthday = normalize_date(value[0]['text'])[0]
                    age = calculate_year_difference(birthday, get_current_date())
                    result['年龄'] = age
            elif key == '年龄':
                if '年龄' not in result:
                    if '岁' in value[0]['text']:
                        age = value[0]['text'].replace(" ", "").replace("岁", "")
                    else:
                        age = calculate_year_difference(normalize_date(value[0]['text'])[0], get_current_date())
                    result['年龄'] = age

            elif key in ['姓名', '籍贯', '学历', '学校', '政治面貌', '求职目标']:
                result[key] = value[0]['text']
            else:
                result[key] = [text_item['text'] for text_item in value]
    return convert_dict_to_entity(file_name, result)


def convert_dict_to_entity(fileName, dict):
    return InformationExtractionResult(
        fileName=fileName,
        name=dict.get('姓名', ''),
        age=str(dict.get('年龄', '')),
        nativePlace=dict.get('籍贯', ''),
        academicQualifications=dict.get('学历', ''),
        school=dict.get('学校', ''),
        politicalAffiliation=dict.get('政治面貌', ''),
        honor=dict.get('荣誉证书', []),
        workYears=str(dict.get('工作年限', '')),
        matchPosition=dict.get('求职目标', '')
    )
