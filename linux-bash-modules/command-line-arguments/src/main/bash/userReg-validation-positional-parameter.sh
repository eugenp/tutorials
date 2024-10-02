# Argument validation check
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <username> <age> <fullname>"
    exit 1
fi
# The Positional Parameters
echo "Username: $1"; 
echo "Age: $2"; 
echo "Full Name: $3";