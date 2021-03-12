## Description
Wei Liu's assignment2 for SOEN6591

Slides: https://docs.google.com/presentation/d/1KbaMDtO5d2aPycI-R6cXKcLZzzbE_Qo1SElT3GO2nN8/edit?usp=sharing

## Output file
### Basic type
/result_destructive_wrapper.txt 
(https://github.com/liuwei-tianshu/WeiLiu-a2_SOEN-6591/blob/main/result_destructive_wrapper.txt)

### Advanced type (calling method)
Most of calling method in catch clause is like this:
```java
catch (Exception e) {
  unassignAsa1000vFromNetwork(network);
  ExceptionUtil.rethrowRuntime(e);
  ExceptionUtil.rethrow(e,InsufficientAddressCapacityException.class);
  ExceptionUtil.rethrow(e,ResourceUnavailableException.class);
  throw new IllegalStateException(e);
}
``` 
However, the function ExceptionUtil is like this: 
```java
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void rethrow(Throwable t, Class<T> clz) throws T {
        if (clz.isAssignableFrom(t.getClass()))
            throw (T)t;
    }

    public static <T extends Throwable> void rethrowRuntime(Throwable t) {
        rethrow(t, RuntimeException.class);
        rethrow(t, Error.class);
    }
``` 
According to the definition of Destructive Wrapping, they are not instances of anti-pattern.

### Output file format
First line in the outputfile: **\<system\>cloudstack-4.9\</system\>\<callsite\>.AgentShell.launchAgentFromClassInfo\</callsite\>\<line\>368\</line\>**

where, **AgentShell** is the class name while **launchAgentFromClassInfo** is the method name, **368** is the line of anti-pattern.

check the Line 368 on Java file AgentShell.java to verify the destructive wrapping :https://github.com/apache/cloudstack/blob/master/agent/src/main/java/com/cloud/agent/AgentShell.java 
