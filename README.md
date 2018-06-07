# bag - Bash Alias Generator

This tool suggests aliases based on non-aliased frequently used commands, and provides a painless way to apply them.


## Build
1. Run:
```bash
$ ./gradlew fatJar
```
2. Create a home directory for the application
3. Into the home directory, copy the files install.sh, build/libs/bag-1.0-SNAPSHOT.jar and build/resources/main/alias.sh
4. From the home directory, run:
```bash
$ source install.sh
```

## -OR- Download from [here][zip] and install:
1. Unzip
2. From the unzipped directory, run:
```bash
$ source install.sh
```

## Usage:
```bash
bag [-c <index> [-a <alternative alias name>]]
 -a,--alternative <alias name>   create an alias with an alternative name
 -c,--create <index>             create an alias with the suggested name
 -h,--help                       print usage
 -l,--print-all                  print a list of all suggested aliases
 -p                              print a list of suggested aliases
 ```

## Examples:
1. Printing a list of suggested aliases
```bash
$ bag -p
SUGGESTED	COMMAND		TIMES USED
(1) fab		foo -a -b -c	42
(2) bde		bar -d -e -f	5
```

2. Creating a suggested alias
```bash
$ bag -c 1
New alias was created: alias fab='foo -a -b -c'
```

3. Creating a suggested alias with an alternative name
```bash
$ bag -c 2 -a bam
New alias was created: alias bam='bar -d -e -f'
```

[zip]: https://github.com/stavshamir/stavshamir.github.io/blob/master/deployed/bag.zip?raw=true