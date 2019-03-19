# C
cut -d: -f1 /etc/passwd | sort | uniq | wc -l

# D
cut -d: -f3 /etc/passwd | sort -g | gawk ' > 99'

# F
cut -d: -f7 /etc/passwd | sort | uniq -c | sort -g

# G
last | cut -d' ' -f1 | sort | uniq -c | sort -g

exit 1
