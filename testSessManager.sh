#!/bin/bash 
for i in `seq 1 2500`;
do
	sh ./callService.sh $i&
done   
