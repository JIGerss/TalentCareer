from enum import Enum


class ResponseStatus(Enum):
    SUCCESS = ("200", "success")
    FAIL = ("501", "failed")
    HTTP_STATUS_200 = ("200", "OK")  # 请求成功
    HTTP_STATUS_400 = ("400", "Request Error")  # 请求错误
    HTTP_STATUS_401 = ("401", "No Authentication")  # 没有认证(token)
    HTTP_STATUS_403 = ("403", "No Authorities")  # 没有权限
    HTTP_STATUS_500 = ("500", "Server Error")  # 服务器内部错误
    HTTP_STATUS_429 = ("429", "Too Many Requests")  # 过多请求

    def __init__(self, response_code, description):
        self.response_code = response_code
        self.description = description

    @property
    def response_code(self):
        return self.value[0]

    @property
    def description(self):
        return self.value[1]

    @staticmethod
    def get_description(response_code):
        for status in ResponseStatus:
            if status.response_code == response_code:
                return status.description
        return None

    @response_code.setter
    def response_code(self, value):
        self._response_code = value

    @description.setter
    def description(self, value):
        self._description = value


HTTP_STATUS_ALL = [
    ResponseStatus.HTTP_STATUS_200,
    ResponseStatus.HTTP_STATUS_400,
    ResponseStatus.HTTP_STATUS_401,
    ResponseStatus.HTTP_STATUS_403,
    ResponseStatus.HTTP_STATUS_500,
    ResponseStatus.HTTP_STATUS_429,
]
