#!/bin/bash

cat <<EOF > ukazi.sh
# C
cut -d: -f1 /etc/passwd | sort | uniq | wc -l

# D
cut -d: -f3 /etc/passwd | sort -g | gawk '$1 > 99'

# F
cut -d: -f7 /etc/passwd | sort | uniq -c | sort -g

# G
last | cut -d' ' -f1 | sort | uniq -c | sort -g

exit 1
EOF

chmod u+x ukazi.sh

cat <<EOF> regex.sh
# D
egrep '\b[a-b][^ ]{2}\b' egreptext

# D2
egrep '\<[0-9][^0-9]{2}\> | \<[^0-9][0-9][^0-9]\> | \<[^0-9]{2}[0-9]\> | \<[^0-9]{3}\>' egreptext

# E
egrep -i '^[^0-9]*[0-9]?[^0-9]*$' egreptext

# F
egrep '^[a-z].*\.$' egreptext

exit 2
EOF

chmod u+x regex.sh