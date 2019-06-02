#!/bin/bash


javac Competition.java


path="/home/jakob/Documents/semster2/APS2/naloga3/train_graphs/";
ER_path="${path}ER*";
SO_path="${path}list.txt";

correct=0;

for file in ${ER_path}; do
    name=$(basename ${file});
    result=$(java Competition < ${file});
    solution=$(grep ${name} ${SO_path});
    solution=(${solution})
    solution=${solution[1]}

#    printf "%s\n" ${result};
#    printf "%s\n" ${solution};

    if [[ ${result} != ${solution} ]]; then
        printf "%s: %s\n" ${name} "FALSE";
        printf "Got: %s\n" ${result};
        printf "Exp: %s\n" ${solution};
    else
        printf "%s: %s\n" ${name} "TRUE";
        printf "Got: %s\n" ${result};
        printf "Exp: %s\n" ${solution};
        (( correct += 1 ));
    fi
done

printf "%d / 32\n" ${correct};