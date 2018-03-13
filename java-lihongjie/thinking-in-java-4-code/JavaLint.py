#!/usr/bin/python
"""
Runs javac -Xlint on all files in all subdirectories.
Collects results into JavaLint.txt
"""
import os

outputfile = "JavaLint.txt"

javadirs = []
for path, dirs, files in os.walk('.'):
    for file in files:
        if file.endswith(".java"):
            javadirs.append(path)
            break

start = os.getcwd()

for jd in javadirs:
  os.chdir(jd)
  print jd
  os.system("javac -source 1.5 -Xlint -Xlint:-serial *.java -Xstdout " + outputfile)
  os.chdir(start)

results = open(start + os.sep + outputfile, 'w')

for jd in javadirs:
  messages = open(jd + os.sep + outputfile).read()
  if len(messages):
    print >>results, '='*40 + "\n" + jd + "\n" + '='*40 + "\n" + messages
    