## Description
Wei Liu's assignment2 for SOEN6591

## Slides
https://docs.google.com/presentation/d/1KbaMDtO5d2aPycI-R6cXKcLZzzbE_Qo1SElT3GO2nN8/edit?usp=sharing

## Output file
/result_destructive_wrapper.txt (https://github.com/liuwei-tianshu/WeiLiu-a2_SOEN-6591/blob/main/result_destructive_wrapper.txt)

## Output file format
First line in the outputfile: **\<system\>cloudstack-4.9\</system\>\<callsite\>.AgentShell.launchAgentFromClassInfo\</callsite\>\<line\>368\</line\>**

where, **AgentShell** is the class name while **launchAgentFromClassInfo** is the method name, **368** is the line of anti-pattern.

check the Line 368 on Java file AgentShell.java to verify it :https://github.com/apache/cloudstack/blob/master/agent/src/main/java/com/cloud/agent/AgentShell.java 

