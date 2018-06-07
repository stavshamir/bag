#!/usr/bin/env bash

echo 'setting BAG_HOME environment variable to' $PWD
export BAG_HOME=$PWD

echo 'creating aliases file'
touch ${BAG_HOME}/aliases

echo 'creating config dir'
[ -d ${BAG_HOME}/config ] || mkdir ${BAG_HOME}/config

echo 'creating config/application.properties'
cat << EOF > ${BAG_HOME}/config/application.properties
bash-history-file=$HOME/.bash_history
alias-user-file=${BAG_HOME}/aliases
alias-script=${BAG_HOME}/alias.sh
EOF

echo 'creating alias for bag'
alias bag='java -jar $BAG_HOME/bag-1.0-SNAPSHOT.jar'

echo 'adding lines to .bashrc'
grep -qF '# bag - bash alias' ~/.bashrc || echo '# bag - bash alias generator' >> ~/.bashrc
grep -qF 'export BAG_HOME'    ~/.bashrc || echo export BAG_HOME=${BAG_HOME}    >> ~/.bashrc
grep -qF 'source '${BAG_HOME} ~/.bashrc || echo source ${BAG_HOME}/aliases     >> ~/.bashrc
grep -qF 'alias bag='         ~/.bashrc || echo alias bag='java -jar $BAG_HOME/bag-1.0-SNAPSHOT.jar' >> ~/.bashrc

echo 'done'