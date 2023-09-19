#!/bin/bash

gunicorn main:app -c gunicorn.py


echo "TalentCareer successfully started!"