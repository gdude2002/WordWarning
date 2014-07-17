**WordWarning** | [Painter](https://github.com/gdude2002/Painter)

WordWarning
===========

Simple bukkit plugin for warning people on certain terms in chat (and then allowing them to continue)

Please see [the page on BukkitDev](http://dev.bukkit.org/bukkit-plugins/wordwarning/) for full usage documentation.

You can find development builds [on Bamboo](http://bamboo.gserv.me/browse/PLUG-WORD).
Remember, they haven't been approved by the BukkitDev staff! Use them at your own risk (but remember, you can check the
source included in the jar).

The latest built jar is always available
[at this directory listing](bamboo.gserv.me/browse/PLUG-WORD/latest/artifact/JOB1/WordWarning/).

Compiling
=========

Compilation of the plugin is fairly simple.

1. Install [the JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) (version 1.7 or higher)
2. Install [Gradle](http://www.gradle.org/)
3. Ensure that the JDK and Gradle are on your system's PATH
4. Open a terminal, `cd` to the project files and `gradle clean build`
5. You'll find the jar in `build/libs/`

I use Gradle instead of Maven simply because I don't like Maven, and Gradle is much easier to work with.
If you need to do Maven things, you can do `gradle install`, which will generate poms and install the plugin
into your local maven repository. Poms are generated in `build/poms/`.