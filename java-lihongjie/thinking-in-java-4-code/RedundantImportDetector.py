"""RedundantImportDetector.py
Discover redundant java imports using brute force.
Requires Python 2.3"""
import os, sys, re
from glob import glob

reportFile = file("RedundantImports.txt", 'w')

startDir = 'D:\\aaa-TIJ4\\code'

# Regular expression to find the block of import statements:
findImports = re.compile("\n(?:import .*?\n)+")

baseDir = os.path.abspath(".")

print "basDir:", baseDir

def main():
    for javaFile in glob("*.java")  + glob("**/*.java"):
            print javaFile
            checkImports(os.path.join(baseDir, javaFile))

def checkImports(javaFile):
    java = file(javaFile).read()
    imports = findImports.search(java)
    if imports:
        imports = [f for f in imports.group(0).split('\n') if f != '']
        fileParts = findImports.split(java)
        assert len(fileParts) == 2
        for mutated in mutateImports(imports):
            file(javaFile, 'w').write(fileParts[0] + mutated + fileParts[1])
            print "changing to", os.path.dirname(javaFile)
            os.chdir(os.path.dirname(javaFile))
            if os.system("javac " + os.path.basename(javaFile)) == 0:
                print >>reportFile, javaFile + "\n" + mutated
                redundantRemoved = "\n".join(
                  [m for m in mutated.split("\n")
                   if not m.startswith("//")])
                print >>reportFile, redundantRemoved
                file(javaFile, 'w').write(fileParts[0] +
                    redundantRemoved + fileParts[1])
                return # No further attempts
    file(javaFile, 'w').write(java) # Restore original file

def mutateImports(imports):
    '''Generates different versions of imports, each with a
    different line commented out'''
    for i in range(len(imports)):
        mutated = imports[:]
        mutated[i] = '//' + mutated[i]
        yield "\n".join([''] + mutated + [''])

if __name__ == "__main__": main()

