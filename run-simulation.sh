#!/bin/bash

for BODIES in 100 1000 5000
do
	for STEPS in 1000 10000 50000
	do
		for THREADS in 1 9 17
		do
			java -jar out/artifacts/pcd_assignment01_jar/pcd-assignment01.jar ${BODIES} ${STEPS} ${THREADS}
		done
	done
done
