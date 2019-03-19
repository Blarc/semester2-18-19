# D
egrep '\b[a-b][^ ]{2}\b' egreptext

# D2
egrep '\<[0-9][^0-9]{2}\> | \<[^0-9][0-9][^0-9]\> | \<[^0-9]{2}[0-9]\> | \<[^0-9]{3}\>' egreptext

# E
egrep -i '^[^0-9]*[0-9]?[^0-9]*$' egreptext

# F
egrep '^[a-z].*\.$' egreptext

exit 2
