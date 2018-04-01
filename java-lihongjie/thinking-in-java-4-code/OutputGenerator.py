#!/usr/bin/python
"""
Runs a Java program, appends output if it's not there

-force as first argument when doing batch files forces overwrite

"""
import os, re, sys

argTag = '// {Args: '
oldOutput = re.compile("/* Output:.*?\n(.*)\n\*///:~(?s)")

def makeOutputIncludedFile(path, fileName, changeReport, force = False):
    oldDir = os.getcwd()
    os.chdir(path)
    base = fileName.split('.')[0]
    package = ''
    args = ''
    command = None
    for line in file(fileName):
        if line.startswith("} /*"):
            break # Out of for loop
        if line.startswith("package"):
            words = line.strip().split()
            package = words[1][:-1] + '.' # remove ';'
        if line.startswith(argTag):
            args = line[len(argTag):].strip()
            assert args.rfind('}') != -1, "%s, %s" % (args, fileName)
            args = " " +args[:args.rfind('}')]
        if line.startswith("// {main:"):
            base = line.split()[-1]
            base = base[:-1]
        if line.startswith("// {Exec:"):
            command = line.split(':', 1)[1].strip()[:-1]
    if not command:
        command = "java " + package + base + args
    command += " > " + base + "-output.txt"
    print command
    result = os.system(command)
    if(result != 0):
        raise Exception, "Command returned nonzero value: " + str(result)
    # Read output file that was just generated:
    results = file(base + "-output.txt").read().strip()
    # Strip off trailing spaces on each line:
    results = "\n".join([line.rstrip() for line in results.split("\n")])
    results = results.replace('\t', '        ')
    if results:
        if force or not oldOutput.findall(file(fileName).read()):
            processedText = createProcessedJavaText(results, fileName)
            open(fileName, 'w').write(processedText + "\n")
            if changeReport:
                changeReport.write(os.path.join(path, fileName) + "\n")
        return # Don't need to try for error output
    ##### Duplicate for standard error output:
    command += " 2> " + base + "-erroroutput.txt"
    print command
    result = os.system(command)
    if(result != 0):
        raise Exception, "Command returned nonzero value: " + str(result)
    # Read error file that was just generated:
    results = file(base + "-erroroutput.txt").read().strip()
    # Strip off trailing spaces on each line:
    results = "\n".join([line.rstrip() for line in results.split("\n")])
    results = results.replace('\t', '        ')
    if results:
        if force or not oldOutput.findall(file(fileName).read()):
            processedText = createProcessedJavaText(results, fileName)
            open(fileName, 'w').write(processedText + "\n")
            if changeReport:
                changeReport.write(os.path.join(path, fileName) + "\n")
    os.chdir(oldDir)

def createProcessedJavaText(results, fileName):
    processedJava = ''
    for line in [line.rstrip() for line in file(fileName)]:
        if line.startswith("} ///:~"):
            processedJava += "} /* Output:\n" + results + "\n*///:~"
            return processedJava
        if line.startswith("} /* Output:"):
            processedJava += line + "\n" + results + "\n*///:~" # Preserve modifiers
            return processedJava
        processedJava += line + "\n"
    raise Exception, "No marker found at end of file " + path + " " + fileName

class ReportFile:
    def __init__(self, filePath):
        self.filePath = filePath
        self.file = None
    def write(self, line):
        if not self.file:
            self.file = file(self.filePath, 'w')
        self.file.write(line)
        print line
    def close(self):
        if self.file:
            self.file.close()

if __name__ == "__main__":
    start = os.getcwd()
    args = sys.argv[1:]
    forceFlag = False
    if len(args):
      if args[0] == "-force":
          forceFlag = True
          print "forceFlag = ", forceFlag
          del args[0]
    if len(args) > 0:
        for javaSource in args:
            if javaSource.endswith("."): javaSource = javaSource[:-1]
            if not javaSource.endswith(".java"): javaSource += ".java"
            os.system("javac " + javaSource)
            makeOutputIncludedFile(os.getcwd(), javaSource, None, force = True)
    else:
        changeReport = ReportFile(os.path.join(start, "Changes.txt"))
        for root, dirs, files in os.walk('.'):
            if (os.sep + "gui") in root: continue
            path = os.path.normpath(os.path.join(start,root))
            print path
            for name in [name for name in files if name.endswith(".java")]:
                java = file(os.path.join(path, name)).read()
                if "public static void main(String" in java and \
                not "{RunByHand}" in java and \
                not "{ThrowsException}" in java and \
                not "/* (Execute to see output) *///:~" in java and \
                not "} /* Same output as" in java:
                    if forceFlag or not "} /* Output:" in java:
                        print "\t", name
                        makeOutputIncludedFile(path, name, changeReport, force = forceFlag)
        changeReport.close()
        os.system("uedit32 /f Changes.txt &")


    