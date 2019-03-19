#!/bin/bash

cat <<EOF > skripta1.sh
# to je komentar $PWD
exit 1
EOF

chmod u+x skripta1.sh

cat <<'EOF'> skripta2.sh
mkdir nov_direktorij
exit 2
EOF

chmod u+x skripta2.sh
