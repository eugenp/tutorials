# Compiling Go Shared Library for All OSes

This guide explains how to compile a Go shared library and set the appropriate environment variables to use the library in your Java application via Java Native Access (JNA).

## Prerequisites

Ensure you have the following installed:
- **Go Programming Language**: [Download Go](https://golang.org/dl/)
- **Java Development Kit (JDK)**: [Download JDK](https://adoptopenjdk.net/)

## Compile the Go Shared Library

**Compile the Go Code** into a shared library. The compilation command varies by operating system:

    - **For Linux**:
      ```bash
      go build -o libhello.so -buildmode=c-shared hello.go
      ```

    - **For macOS**:
      ```bash
      go build -o libhello.dylib -buildmode=c-shared hello.go
      ```

    - **For Windows**:
      ```bash
      go build -o libhello.dll -buildmode=c-shared hello.go
      ```


## Setting Environment Variables

After compiling the shared library, set the appropriate environment variables so Java can load the shared library.

### Linux

Run the following command to set the `LD_LIBRARY_PATH`:
```bash
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.
```

### Windows

Add the directory containing libhello.dll to the PATH environment variable. You can do this by modifying the System Properties > Environment Variables > PATH variable.

### Linux

Run the following command to set the `DYLD_LIBRARY_PATH`:
```bash
export DYLD_LIBRARY_PATH=$DYLD_LIBRARY_PATH:.
```
