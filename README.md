Four wins against the computer
====
----

With Windows as operating system:
<br>
Use this start file:
```
./external/start.bat
```
----

With Linux as operating system:
<br>
Use this start file:
```
./external/start.sh
```
----

Or if docker is installed:
<br>
Use this start file:
```
docker run -i -t connectfour:<VERSION>
```
<br>
In order to guarantee the input the following tags must be set at the start:

| Name, shorthand        | Description                          |
|------------------------|--------------------------------------|
| `--tty` , `-t`         | Allocate a pseudo-TTY                |
| `--interactive` , `-i` | Keep STDIN open even if not attached |

Source of tags and more detailed information at: https://docs.docker.com/engine/reference/commandline/run/

----