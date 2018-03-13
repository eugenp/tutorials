"""FindBugsExcluder.py
Creates a filter file from the xml and text output of FindBugs
To prepare, you must run
findbugs -textui . > findbugs.txt
findbugs -textui -xml . > findbugs.xml
Once you've run this program you can then run
findbugs -textui -exclude FindBugsFilter-auto.xml .
To exclude the bugs that have been discovered.

The program includes the suggested changes with each exclusion,
so you can go through FindBugsFilter-auto.xml and decide
to fix things and remove their "Match" nodes.
"""
from xml.dom.minidom import parse
import xml.dom
import os, sys, re, pprint

xml_buglist = 'findbugs.xml' #'D:\\aaa-TIJ4\\code\\findbugs.xml'
text_buglist = 'findbugs.txt' # 'D:\\aaa-TIJ4\\code\\findbugs.txt'
findbugs_filter = 'FindBugsFilter-auto.xml' # 'D:\\aaa-TIJ4\\code\\FindBugsFilter-auto.xml'

def main():
    textbugs = [bug.split(':', 1) for bug in file(text_buglist)
                if bug.startswith("M ") or bug.startswith("H ")]
    textbugs = [(bug[0].split()[2], bug[1].strip()) for bug in textbugs]
    dom1 = parse(xml_buglist)
    dom2 = xml.dom.getDOMImplementation().createDocument(
               None, "FindBugsFilter", None)
    bugsDone = []
    for bugNode in [bug for bug in dom1.firstChild.childNodes
                    if bug.nodeName == "BugInstance"]:
        for child in bugNode.childNodes:
            if child.nodeName == "Class":
                classname = child.attributes.item(0).value
                bugtype = bugNode.attributes.item(2).value
                if (bugtype, classname) in bugsDone:
                    continue
                else:
                    bugsDone.append((bugtype, classname))
                match = dom2.createElement("Match")
                match.setAttribute("class", classname)
                bugCode = dom2.createElement("BugCode")
                bugCode.setAttribute("name",  bugtype)
                match.appendChild(bugCode)
                for textbug in textbugs:
                    if textbug[0] == bugtype and classname in textbug[1]:
                        match.appendChild(dom2.createComment(textbug[1]))
                dom2.documentElement.appendChild(match)
                break # out of inner for loop
    file(findbugs_filter, 'w').write(dom2.toprettyxml('    ', '\n'))

if __name__ == "__main__": main()

