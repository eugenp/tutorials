#!/usr/bin/python
"""
Eclipse.py by Bruce Eckel, for Thinking in Java 4e
Modify or insert package statments so that Eclipse is happy with the code tree.
Run this with no arguments from the root of the code tree.

The Ant build will not work once you run this program!

You may also want to modify the dotproject and dotclasspath text below.

You must have Python 2.3 installed to run this program. See www.python.org.
"""
import os

os.remove("reusing/Lisa.java");

for path, dirs, files in os.walk('.'):
    for file in files:
        if file.endswith(".java"):
            filepath = path + os.sep + file
            firstLine = open(filepath).readline().strip()
            tagPath = firstLine.split()[1]
            tagPath = ".".join(tagPath.split('/')[:-1])
            packageStatement = "package " + tagPath + ";"
            code = open(filepath).readlines()
            found = False
            for line in code:
                if line.startswith("package "):
                    found = True
            if not found:
                code.insert(1, packageStatement + " /* Added by Eclipse.py */\n")
                open(filepath, 'w').writelines(code)

here = os.path.abspath('.').replace("\\", "/")
if here.startswith("/cygdrive/"): # If using cygwin
    here = here.replace("/cygdrive/", "", 1)
    here = here[0] + ":" + here[1:]
print "here", here
open(".classpath", 'w').write(\
"""<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry excluding="polymorphism/|holding/|flow/|exceptions/|concurrency/|typeinfo/|innerclasses/|arrays/|interfaces/|reusing/|initialization/|cloning/|io/|containers/|generics/|xml/|hiding/|io/xfiles/|passing/|gui/|annotations/|enumerated/|discovering/|object/|strings/|swt/" kind="src" path=""/>
	<classpathentry kind="src" path="annotations"/>
	<classpathentry kind="src" path="arrays"/>
	<classpathentry kind="src" path="cloning"/>
	<classpathentry kind="src" path="concurrency"/>
	<classpathentry kind="src" path="containers"/>
	<classpathentry kind="src" path="discovering"/>
	<classpathentry kind="src" path="enumerated"/>
	<classpathentry kind="src" path="exceptions"/>
	<classpathentry kind="src" path="flow"/>
	<classpathentry kind="src" path="generics"/>
	<classpathentry kind="src" path="gui"/>
	<classpathentry kind="src" path="hiding"/>
	<classpathentry kind="src" path="holding"/>
	<classpathentry kind="src" path="initialization"/>
	<classpathentry kind="src" path="innerclasses"/>
	<classpathentry kind="src" path="interfaces"/>
	<classpathentry excluding="xfiles/" kind="src" path="io"/>
	<classpathentry kind="src" path="io/xfiles"/>
	<classpathentry kind="src" path="object"/>
	<classpathentry kind="src" path="passing"/>
	<classpathentry kind="src" path="polymorphism"/>
	<classpathentry kind="src" path="reusing"/>
	<classpathentry kind="src" path="strings"/>
	<classpathentry kind="src" path="swt"/>
	<classpathentry kind="src" path="typeinfo"/>
	<classpathentry kind="src" path="xml"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jre1.5.0_01"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
""") # % (here, here))

open(".project", 'w').write(\
"""<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
    <name>TIJ4</name>
    <comment></comment>
    <projects>
    </projects>
    <buildSpec>
        <buildCommand>
            <name>org.eclipse.jdt.core.javabuilder</name>
            <arguments>
            </arguments>
        </buildCommand>
    </buildSpec>
    <natures>
        <nature>org.eclipse.jdt.core.javanature</nature>
    </natures>
</projectDescription>

""")

if not os.path.exists(".settings"):
    os.mkdir(".settings")
os.chdir(".settings")
open("org.eclipse.jdt.core.prefs", 'w').write(\
"""#Fri Jan 14 11:03:37 MST 2005
org.eclipse.jdt.core.compiler.debug.localVariable=generate
org.eclipse.jdt.core.compiler.compliance=1.5
org.eclipse.jdt.core.compiler.codegen.unusedLocal=preserve
org.eclipse.jdt.core.compiler.debug.sourceFile=generate
org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.5
org.eclipse.jdt.core.compiler.problem.enumIdentifier=error
eclipse.preferences.version=1
org.eclipse.jdt.core.compiler.debug.lineNumber=generate
org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled
org.eclipse.jdt.core.compiler.source=1.5
org.eclipse.jdt.core.compiler.problem.assertIdentifier=error
""")

print """Project ready to be opened with Eclipse (see www.Eclipse.org)
Use DEclipse.py if you want to go back to building with Ant."""
       