from fastapi import FastAPI

from common import *
from model import InformationExtraction

app = FastAPI()


@app.post("/information_extraction", response_model=ResponseResult)
async def information_extraction(request: InformationExtractionRequest):
    result = InformationExtraction.exec(request.fileName, request.content)
    return ResponseResult.success(result)


@app.get("/hello")
def hello():
    return ResponseResult.success()

# uvicorn main:app --reload --port 7777
