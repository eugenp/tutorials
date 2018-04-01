#!/usr/bin/python
"""
To do:

3) command-line argument (to test a single file)

- What about exceptions and aborts?

-If ...is embedded anywhere in a line, that portion becomes a .*? regexp

---------------
Find files with
/* Output:

Run the programs and capture the output, compare with anticipated output.

/* Output: (80% match)

For files that vary from run to run

Complete punt:
/* Output: (Sample)

(More elaborate design in SimpleTest1.py)
"""
import os, re, glob, sys, string, codecs
from difflib import SequenceMatcher

argTag = '// {Args: '
targetOutput = re.compile("/* Output:(.*?)\n(.*)\n\*///:~", re.DOTALL)

class SimpleTest:

    def __init__(self, fileName, text, referencePath, reportFile):
        self.fileName = fileName
        self.normalOutput = self.fileName + "-output.txt"
        self.errorOutput = self.fileName + "-erroroutput.txt"
        self.text = text
        self.referencePath = referencePath
        self.reportFile = reportFile
        self.package = ""
        self.args = ""
        self.runTest = True
        self.insertOutput = True
        self.EmbeddedComparisonOutput = False
        self.comparisonFile = None
        self.lines = self.text.split("\n")
        for line in self.lines:
            if "{RunByHand}" in line or \
                line.startswith("import javax.swing.*;") or \
                "c12:ZipCompress.java" in line or \
                "/* (Execute to see output) *///:~" in line:
                    self.runTest = False
            if line.startswith("package"):
                self.package = line.split()[1][:-1] + "."
            if line.startswith(argTag):
                self.args = line[len(argTag):].strip()
                assert self.args.rfind('}') != -1, "%s, %s" % (self.args, referencePath)
                self.args = self.args[:self.args.rfind('}')]
            if line.startswith("// {main:"):
                self.fileName = line.split()[-1][:-1]
            if line.startswith("// {Exec:"):
                self.command = line.split(':', 1)[1].strip()[:-1]
            if "/* Output:" in line:
                self.EmbeddedComparisonOutput = True
            if line.startswith("} /*"):
                break # Out of for loop
            #if "}  ///:~" in line: # Extra space
            #    self.insertOutput = False

    def run(self):
        if not self.runTest: return
        if not hasattr(self, "command"):
            self.command = "java " + self.package + self.fileName + " " + self.args
        # Capture standard output into a local file.
        self.command = self.command + " > " + self.normalOutput
        print self.command
        os.system(self.command)
        if os.stat(self.normalOutput).st_size:
            return self.compareResults(self.normalOutput)
        # Capture error output into a local file.
        # The '2>' requires cygwin under Windows, or *nix:
        self.command = self.command + " 2> " + self.errorOutput
        print self.command
        os.system(self.command)
        return self.compareResults(self.errorOutput)

    def compareResults(self, fileName):
        # Read output file that was just generated:
        results = makePrintable(file(fileName).read())
        results = results.replace('\t', '        ')
        results = results.strip()
        file("Generated.txt",'w').write(results)
        # Strip off trailing spaces on each line:
        results = "\n".join([line.rstrip() for line in results.split("\n")])
        controlSample = self.getControlSample()
        ratio = 1.0
        if controlSample:
            controlOutput = controlSample.group(2).rstrip()
            if "\n..." in controlOutput:
                controlLines = controlOutput.split("\n")[:-1]
                resultLines = results.split("\n")[:len(controlLines)]
                controlOutput = "\n".join(controlLines)
                results = "\n".join(resultLines)
            file("controlOutput.txt",'w').write(controlOutput)
            modifier = controlSample.group(1)
            if "match" in modifier:
                ratio = float(re.findall("\d+", modifier)[0]) / 100
                print "Looking for", ratio, "match"
            if "Sample" in modifier:
                ratio = 0.0
            actualRatio = SequenceMatcher(None, controlOutput, results).ratio()
            if actualRatio < ratio:
                self.reportFile.write("mismatch in " + self.referencePath + "\n")
                self.reportFile.write("Actual ratio " + str(actualRatio) + "\n")
                self.reportFile.write("expected:\n")
                self.reportFile.write(controlOutput + "\n")
                self.reportFile.write("----------actual:----------\n")
                self.reportFile.write(results + "\n")
                file(self.fileName + "-control.txt", 'w').write(controlOutput)
                file(self.fileName + "-results.txt", 'w').write(results)
                self.reportFile.write("---------------------------\n")
                os.system("cmp " + self.fileName + "-control.txt "
                          + self.fileName + "-results.txt"
                          + " > cmp-out.txt")
                self.reportFile.write(file("cmp-out.txt").read())
                self.reportFile.write("=" * 40 + "\n")

        else:
            pass #!!! No control sample, create initial one here

    def appendOutput(self):
        if self.insertOutput:
            # Rewrite the tail of the source file if the result is nonzero
            self.lines[-2] = '}'
            self.lines[-1] = "/* Output:"
            for tline in file(self.fileName + "-output.txt"):
                self.lines.append(tline.rstrip())
            self.lines.append("*///:~")
            self.lines.append("")
            file(self.fileName + ".java", 'w').write("\n".join(self.lines))

    def getControlSample(self):
        """Finds the control sample, returns an re group
        First element is the arguments, second is the actual data"""
        if self.EmbeddedComparisonOutput:
            self.sourceOutput = targetOutput.search(self.text)
        else:
            return None
        return self.sourceOutput

def makePrintable(s):
    for c in s:
        if c not in string.printable: return _makePrintable(s)
    return s

def _makePrintable(s):
    result = ''
    for c in s:
        if c not in string.printable: result += ' '
        else: result += c
    return result

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
    if len(sys.argv) > 1:
        javaSource = sys.argv[1]
        if javaSource.endswith("."): javaSource = javaSource[:-1]
        if not javaSource.endswith(".java"): javaSource += ".java"
        os.system("javac " + javaSource)
        SimpleTest(javaSource.split('.')[0], file(javaSource).read(), javaSource, sys.stdout).run()
        sys.exit()
    start = os.getcwd()
    reportFile = ReportFile(start + os.sep + "OutputErrors.txt")
    for root, dirs, files in os.walk('.'):
        print root
        os.chdir(root)
        for f in [name.split('.')[0] for name in files if name.endswith(".java")]:
            text = file(f + ".java").read()
            # Only perform verification if there is an output tag:
            if text.find("/* Output:") != -1:
                referencePath = os.path.join(root, f + ".java")
                SimpleTest(f, text, referencePath, reportFile).run()
        os.chdir(start)
    reportFile.close()
    if reportFile.file:
        print "Errors in OutputErrors.txt"

