This module contains 2 sub-modules:

1) triton server with pre-loaded model(should be downloaded)
2) the sentiment-service in java with apache-camel-kserve

The modules both contain a Dockerfile and can be easily deployed locally using docker-compose.yml

First, you need to download the model from [huggingface](https://huggingface.co/pjxcharya/onnx-sentiment-model/tree/main) and place it in triton-server/models/sentiment/1.
Then execute:

```bash
docker-compose up --build
```

The endpoint to test everything works is: `http://localhost:8080/sentiments?sentence=i probably like you`
