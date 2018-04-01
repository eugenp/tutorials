#!/usr/bin/python
"""
DEclipse.py by Bruce Eckel, for Thinking in Java 4e

Undoes the effect of Eclipse.py, so that Ant can be used
again to build the code tree.

You must have Python 2.3 installed to run this program. See www.python.org.
"""
import os

for path, dirs, files in os.walk('.'):
    for file in files:
        if file.endswith(".java"):
            filepath = path + os.sep + file
            code = open(filepath).readlines()
            found = False
            for n, line in enumerate(code):
                if line.find(" /* Added by Eclipse.py */") != -1:
                    del code[n]
            open(filepath, 'w').writelines(code)


print "Project ready to be built with Ant."
       