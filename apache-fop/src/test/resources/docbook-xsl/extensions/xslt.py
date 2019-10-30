#!/usr/bin/python -u
# $Id: xslt.py 8353 2009-03-17 16:57:50Z mzjn $

import sys
import libxml2
import libxslt
from docbook import adjustColumnWidths

# Check the arguments
usage = "Usage: %s xmlfile.xml xslfile.xsl [outputfile] [param1=val [param2=val]...]" % sys.argv[0]

xmlfile = None
xslfile = None
outfile = "-"
params  = {}

try:
    xmlfile = sys.argv[1]
    xslfile = sys.argv[2]
except IndexError:
    print usage
    sys.exit(1)

def quote(astring):
    if astring.find("'") < 0:
        return "'" + astring + "'"
    else:
        return '"' + astring + '"'

try:
    outfile = sys.argv[3]
    if outfile.find("=") > 0:
        name, value = outfile.split("=", 2)
        params[name] = quote(value)
        outfile = None

    count = 4
    while (sys.argv[count]):
        try:
            name, value = sys.argv[count].split("=", 2)
            if params.has_key(name):
                print "Warning: '%s' re-specified; replacing value" % name
            params[name] = quote(value)
        except ValueError:
            print "Invalid parameter specification: '" + sys.argv[count] + "'"
            print usage
            sys.exit(1)
        count = count+1
except IndexError:
    pass

# ======================================================================
# Memory debug specific
# libxml2.debugMemory(1)

# Setup environment
libxml2.lineNumbersDefault(1)
libxml2.substituteEntitiesDefault(1)
libxslt.registerExtModuleFunction("adjustColumnWidths",
                                  "http://nwalsh.com/xslt/ext/xsltproc/python/Table",
                                  adjustColumnWidths)

# Initialize and run
styledoc = libxml2.parseFile(xslfile)
style = libxslt.parseStylesheetDoc(styledoc)
doc = libxml2.parseFile(xmlfile)
result = style.applyStylesheet(doc, params)

# Save the result
if outfile:
    style.saveResultToFilename(outfile, result, 0)
else:
    print result

# Free things up
style.freeStylesheet()
doc.freeDoc()
result.freeDoc()

# Memory debug specific
#libxslt.cleanup()
#if libxml2.debugMemory(1) != 0:
#    print "Memory leak %d bytes" % (libxml2.debugMemory(1))
#    libxml2.dumpMemory()
