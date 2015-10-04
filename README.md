This project has been abandoned
===============================

This is because I am no longer involved with Minecraft and my community has been shut down.

Feel free to fork and update the project for your own purposes, but I will no longer be maintaining it.

---

WordWarning
===========

My plugins: **WordWarning** | [Painter](https://github.com/gdude2002/Painter) | [LotteryBox](https://github.com/gdude2002/LotteryBox)

---

Simple bukkit plugin for warning people on certain terms in chat (and then allowing them to continue)

Please see [the page on BukkitDev](http://dev.bukkit.org/bukkit-plugins/wordwarning/) for full usage documentation.

You can find development builds [on Bamboo](http://bamboo.gserv.me/browse/PLUG-WORD).
Remember, they haven't been approved by the BukkitDev staff! Use them at your own risk (but remember, you can check the
source included in the jar).

The latest built jar is always available
[here](http://bamboo.gserv.me/browse/PLUG-WORD/latest/artifact/JOB1/Version-agnostic-jar/WordWarning.jar).

Maven/Ivy repos
===============

Gradle automatically builds the following repos for your use.

* Ivy: http://cherry.gserv.me/repos/ivy/
* Maven: http://cherry.gserv.me/repos/maven/

This plugin doesn't have an API, but if you need to use it as a dependency, you can use the above repos.

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

---

My plugins: **WordWarning** | [Painter](https://github.com/gdude2002/Painter) | [LotteryBox](https://github.com/gdude2002/LotteryBox)
