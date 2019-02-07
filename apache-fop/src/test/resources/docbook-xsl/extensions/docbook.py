# docbook.py: extension module
# $Id: docbook.py 8353 2009-03-17 16:57:50Z mzjn $

import sys
import string
import libxml2
import libxslt
import re
import math

# Some globals
pixelsPerInch = 96.0
unitHash = { 'in': pixelsPerInch,
             'cm': pixelsPerInch / 2.54,
             'mm': pixelsPerInch / 25.4,
             'pc': (pixelsPerInch / 72.0) * 12,
             'pt': pixelsPerInch / 72.0,
             'px': 1 }

# ======================================================================

def adjustColumnWidths(ctx, nodeset):
    #
    # Small check to verify the context is correcly accessed
    #
    try:
        pctxt = libxslt.xpathParserContext(_obj=ctx)
        ctxt = pctxt.context()
        tctxt = ctxt.transformContext()
    except:
        pass

    # Get the nominal table width
    varString = lookupVariable(tctxt, "nominal.table.width", None)
    if varString == None:
        nominalWidth = 6 * pixelsPerInch;
    else:
        nominalWidth = convertLength(varString);

    # Get the requested table width
    tableWidth = lookupVariable(tctxt, "table.width", "100%")

    foStylesheet = (tctxt.variableLookup("stylesheet.result.type", None) == "fo")

    relTotal = 0
    relParts = []

    absTotal = 0
    absParts = []

    colgroup = libxml2.xmlNode(_obj = nodeset[0])
    # If this is an foStylesheet, we've been passed a list of fo:table-columns.
    # Otherwise we've been passed a colgroup that contains a list of cols.
    if foStylesheet:
        colChildren = colgroup
    else:
        colChildren = colgroup.children

    col = colChildren
    while col != None:
        if foStylesheet:
            width = col.prop("column-width")
        else:
            width = col.prop("width")

        if width == None:
            width = "1*"

        relPart = 0.0
        absPart = 0.0
        starPos = string.find(width, "*")
        if starPos >= 0:
            relPart, absPart = string.split(width, "*", 2)
            relPart = float(relPart)
            relTotal = relTotal + float(relPart)
        else:
            absPart = width

        pixels = convertLength(absPart)
        absTotal = absTotal + pixels

        relParts.append(relPart)
        absParts.append(pixels)

        col = col.next

    # Ok, now we have the relative widths and absolute widths in
    # two parallel arrays.
    #
    # - If there are no relative widths, output the absolute widths
    # - If there are no absolute widths, output the relative widths
    # - If there are a mixture of relative and absolute widths,
    #   - If the table width is absolute, turn these all into absolute
    #     widths.
    #   - If the table width is relative, turn these all into absolute
    #     widths in the nominalWidth and then turn them back into
    #     percentages.

    widths = []

    if relTotal == 0:
        for absPart in absParts:
            if foStylesheet:
                inches = absPart / pixelsPerInch
                widths.append("%4.2fin" % inches)
            else:
                widths.append("%d" % absPart)
    elif absTotal == 0:
        for relPart in relParts:
            rel = relPart / relTotal * 100
            widths.append(rel)
        widths = correctRoundingError(widths)
    else:
        pixelWidth = nominalWidth
        if string.find(tableWidth, "%") < 0:
            pixelWidth = convertLength(tableWidth)

        if pixelWidth <= absTotal:
            print "Table is wider than table width"
        else:
            pixelWidth = pixelWidth - absTotal

        absTotal = 0
        for count in range(len(relParts)):
            rel = relParts[count] / relTotal * pixelWidth
            relParts[count] = rel + absParts[count]
            absTotal = absTotal + rel + absParts[count]

        if string.find(tableWidth, "%") < 0:
            for count in range(len(relParts)):
                if foStylesheet:
                    pixels = relParts[count]
                    inches = pixels / pixelsPerInch
                    widths.append("%4.2fin" % inches)
                else:
                    widths.append(relParts[count])
        else:
            for count in range(len(relParts)):
                rel = relParts[count] / absTotal * 100
                widths.append(rel)
            widths = correctRoundingError(widths)

    # Danger, Will Robinson! In-place modification of the result tree!
    # Side-effect free? We don' need no steenkin' side-effect free!
    count = 0
    col = colChildren
    while col != None:
        if foStylesheet:
            col.setProp("column-width", widths[count])
        else:
            col.setProp("width", widths[count])

        count = count+1
        col = col.next

    return nodeset

def convertLength(length):
    # Given "3.4in" return the width in pixels
    global pixelsPerInch
    global unitHash

    m = re.search('([+-]?[\d\.]+)(\S+)', length)
    if m != None and m.lastindex > 1:
        unit = pixelsPerInch
        if unitHash.has_key(m.group(2)):
            unit = unitHash[m.group(2)]
        else:
            print "Unrecognized length: " + m.group(2)

        pixels = unit * float(m.group(1))
    else:
        pixels = 0

    return pixels

def correctRoundingError(floatWidths):
    # The widths are currently floating point numbers, we have to truncate
    # them back to integers and then distribute the error so that they sum
    # to exactly 100%.

    totalWidth = 0
    widths = []
    for width in floatWidths:
        width = math.floor(width)
        widths.append(width)
        totalWidth = totalWidth + math.floor(width)

    totalError = 100 - totalWidth
    columnError = totalError / len(widths)
    error = 0
    for count in range(len(widths)):
        width = widths[count]
        error = error + columnError
        if error >= 1.0:
            adj = math.floor(error)
            error = error - adj
            widths[count] = "%d%%" % (width + adj)
        else:
            widths[count] = "%d%%" % width

    return widths

def lookupVariable(tctxt, varName, default):
    varString = tctxt.variableLookup(varName, None)
    if varString == None:
        return default

    # If it's a list, get the first element
    if type(varString) == type([]):
        varString = varString[0]

    # If it's not a string, it must be a node, get its content
    if type(varString) != type(""):
        varString = varString.content

    return varString

# ======================================================================
# Random notes...

#once you have a node which is a libxml2 python xmlNode wrapper all common
#operations are possible:
#   .children .last .parent .next .prev .doc for navigation
#   .content .type for introspection
#   .prop("attribute_name") to lookup attribute values

#    # Now make a nodeset to return
#    # Danger, Will Robinson! This creates a memory leak!
#    newDoc = libxml2.newDoc("1.0")
#    newColGroup = newDoc.newDocNode(None, "colgroup", None)
#    newDoc.addChild(newColGroup)
#    col = colgroup.children
#    while col != None:
#        newCol = newDoc.newDocNode(None, "col", None)
#        newCol.copyPropList(col);
#        newCol.setProp("width", "4")
#        newColGroup.addChild(newCol)
#        col = col.next
