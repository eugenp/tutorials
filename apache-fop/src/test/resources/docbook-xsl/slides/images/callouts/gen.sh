#!/bin/sh

# $FreeBSD$

#
# This script was taken from FreeBSD.
#
# It uses ImageMagick to generate callout icons.
#

for i in `jot 9 1`
do
    convert -size 202x202 xc:green -transparent green -fill black -draw 'circle 100,100 100' -fill white -stroke none -pointsize 160 -gravity center -kerning -5 -font Helvetica-bold -draw "text 0,5 \"$i\"" -scale '24x24' $i.png
    convert -size 202x202 xc:green -transparent green -fill black -draw 'circle 100,100 100' -fill white -stroke none -pointsize 160 -gravity center -kerning -5 -font Helvetica-bold -draw "text 0,5 \"$i\"" -scale '24x24' $i.svg
done

for i in `jot 21 10`
do
    convert -size 202x202 xc:green -transparent green -fill black -draw 'circle 100,100 100' -fill white -stroke none -pointsize 140 -gravity center -kerning -5 -font Helvetica-bold -draw "text 0,5 \"$i\"" -scale '24x24' $i.png
    convert -size 202x202 xc:green -transparent green -fill black -draw 'circle 100,100 100' -fill white -stroke none -pointsize 140 -gravity center -kerning -5 -font Helvetica-bold -draw "text 0,5 \"$i\"" -scale '24x24' $i.svg
done

exit 0
