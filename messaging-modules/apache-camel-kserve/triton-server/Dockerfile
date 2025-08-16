FROM nvcr.io/nvidia/tritonserver:25.02-py3

# Copy the model repository into the container
COPY models/ /models/

# Expose default Triton ports
EXPOSE 8000 8001 8002

# Set entrypoint to run Triton with your model repo
CMD ["tritonserver", "--model-repository=/models"]
