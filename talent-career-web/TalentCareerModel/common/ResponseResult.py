from datetime import datetime
from typing import Optional, TypeVar

from pydantic import BaseModel

from common import ResponseStatus

T = TypeVar('T')


class ResponseResult(BaseModel):
    time: str
    status: str
    message: str
    data: Optional[T]

    @staticmethod
    def success(data=None):
        return ResponseResult(
            time=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            status="200",
            message="OK",
            data=data
        )

    @staticmethod
    def fail(message, data=None):
        return ResponseResult(
            time=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            status="500",
            message=message,
            data=data
        )

    @staticmethod
    def base(status, data=None, message=None):
        return ResponseResult(
            time=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            status=status,
            message=message or ResponseStatus.get_description(status),
            data=data
        )
