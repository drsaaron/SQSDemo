#! /bin/sh

while getopts :sjf OPTION
do
    case $OPTION in
	s)
	    profile=sqs
	    ;;
	j)
	    profile=jms
	    ;;
	f)
	    doFail=true
	    ;;
	*)
	    echo "unknown option $OPTARG"
	    exit 1
    esac
done

if [ -z "$profile" ]
then
    echo "choose a profile" 1>&2
    exit 1
fi

shift $((OPTIND-1))

if [ $# = 0 ]
then
    echo "need some names" 1>&2
    exit 1
fi

java -Ddemo.doFail=${doFail:-false} -Dspring.profiles.active=$profile -jar target/$(getPomAttribute.sh artifactId)-$(getPomAttribute.sh version).jar $*

