
while getopts u:a:f: flag
do
    case "${flag}" in
        u) username=${OPTARG};;
        a) age=${OPTARG};;
        f) fullname=${OPTARG};;
    esac
done
#Now handle positional arguments
shift $((OPTIND - 1))
param1=$1 # First positional argument 
param2=$2 # Second positional argument 
param3=$3 # Third positional argument
echo "Username: $username"; 
echo "Age: $age"; 
echo "Full Name: $fullname";
echo "First Positional Parameter: $param1";
echo "Second Positional Parameter: $param2";
echo "Third Positional Parameter: $param3";
