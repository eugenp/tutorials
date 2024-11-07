#!/bin/bash
bucket_name="baeldung-bucket"

awslocal s3api create-bucket --bucket $bucket_name

echo "S3 bucket '$bucket_name' created successfully"
echo "Executed init-s3-bucket.sh"