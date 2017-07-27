#!/bin/bash
# vim: number

# docbook-xsl-update - Update environment to latest docbook-xsl snapshot
# $Id: docbook-xsl-update 9628 2012-10-20 23:52:01Z dcramer $

if [ -z $DOCBOOK_MIRROR ]; then
  myhost=docbook.xml-doc.org:5873;
else
  myhost=$DOCBOOK_MIRROR;
fi

mydir=$(readlink -f $(dirname $0))
mydocbook_xsl_base=$(readlink -f $(dirname $0)/../..)

if [ ! -f $mydocbook_xsl_base/VERSION.xsl ]; then 
  cat <<- EOF
$(basename $0): error: not in snapshot directory. Stopping.

The $(basename $0) script must be installed within its original
location in the tools/bin directory in the docbook-xsl distribution.
EOF
  exit 1
fi

usage="Usage:

  $(basename $0) [-h HOST[:PORT]]

  -h HOST[:PORT]  Specifies the rsync host and port number to use.
                  If not specified, uses the value of the
                  \$DOCBOOK_MIRROR environment variable. If that
                  environment variable is not specified, defaults
                  to a hard-coded value.

"

while getopts "h:" opt; do
  case $opt in
    h  ) myhost=$OPTARG ;;
    \? ) printf "$usage"
         printf "$opts_admon"
         exit 1 ;;
  esac
done

shift $(($OPTIND - 1))

if [ -z $myhost ]; then
  myhost=$DOCBOOK_MIRROR
fi

rsync -auv rsync://$myhost/xsl $mydocbook_xsl_base
