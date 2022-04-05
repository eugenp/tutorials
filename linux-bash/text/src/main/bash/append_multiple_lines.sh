#!/bin/bash
# echo per line
echo Lorem ipsum dolor sit amet, consectetur adipiscing elit, >> echo-per-line.txt
echo sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. >> echo-per-line.txt

# echo with escaped newline
echo -e Lorem ipsum dolor sit amet, consectetur adipiscing elit,\\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. >> echo-escaped-newline.txt

# echo with double quoted string
echo -e "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua." >> echo-double-quoted.txt

# printf instead of echo
printf "Lorem ipsum dolor sit amet, consectetur adipiscing elit,\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua." >> printf.txt

# printf using format string
printf "%s\n%s" "Lorem ipsum dolor sit amet, consectetur adipiscing elit," "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." >> printf-format.txt

# cat
cat << EOF >> cat.txt
Lorem ipsum dolor sit amet, consectetur adipiscing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
EOF

# tee
tee -a tee.txt << EOF
Lorem ipsum dolor sit amet, consectetur adipiscing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
EOF

