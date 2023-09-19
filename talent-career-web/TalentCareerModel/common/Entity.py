from typing import List

from pydantic import BaseModel


class InformationExtractionRequest(BaseModel):
    fileName: str
    content: str


class InformationExtractionResult(BaseModel):
    fileName: str
    name: str
    age: str
    nativePlace: str
    academicQualifications: str
    school: str
    politicalAffiliation: str
    honor: List[str]
    workYears: str
    matchPosition: str
