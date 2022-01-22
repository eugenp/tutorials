#include<stdio.h>
#include<stdlib.h>

char* printName(char *name) {
    char* newString = (char*)malloc((15 + sizeof(name))*sizeof(char));
    sprintf(newString, "Your name is %s", name);
    return newString;
}